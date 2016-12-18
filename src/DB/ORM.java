package DB;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 17.12.2016.
 */

/**
 * тут должно было быть все реализовано с помощью рефлексии, но теперь он не используется
 */
public class ORM {

    Field[] fields;


    public ArrayList<ArrayList> selectAll(Class c) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        Connection conn = DBWrapper.getConnection();


        ArrayList<ArrayList> list = new ArrayList<ArrayList>();
        ArrayList<Object> values = null;

        fields = c.getDeclaredFields();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM " + c.getName() + "s");


        while (rs.next()) {
            values = new ArrayList<Object>();
            for (Field f : fields) {
                String type = String.valueOf(f.getType());

                if (type.equals("int")) {
                    values.add(rs.getInt(f.toString().replace("int " + c.getName() + ".", "")));

                } else if (type.equals("class java.lang.String")) {
                    values.add(rs.getString(f.toString().replace("java.lang.String " + c.getName() + ".", "")));
                }

            }
            list.add(values);
        }
        return list;
    }

    public void insert(Class c, Object instance) throws NoSuchMethodException, SQLException, InvocationTargetException, IllegalAccessException {
        Connection conn = DBWrapper.getConnection();

//        fields = c.getDeclaredFields();
        fields=instance.getClass().getDeclaredFields();
        Method[] methods = c.getMethods();
        List<String> methodsNames = new ArrayList<String>();
        List<Method> methodList = new ArrayList<Method>();
        for(int i=0; i<fields.length; i++) {
            System.out.println(fields[i].getName().substring(1));
            for (Method method : methods) {
                if (method.getName().startsWith("get")&method.getName().endsWith(fields[i].getName().substring(1))) {
                    methodsNames.add(method.getName());
                    break;
                }
                System.out.println(fields[i].getName().substring(1));
            }
        }
//        for (int i = 0; i < methodsNames.size()-1; i++) {
        for (int i = 0; i < methodsNames.size(); i++) {
            Method method = c.getMethod(methodsNames.get(i));
            methodList.add(method);
        }

        String preparedString = "INSERT INTO " +c.getSimpleName()+"s (";
        for (Field field:fields){
            preparedString=preparedString+field.getName()+",";
            System.out.println(field.getName());
        }
        preparedString = preparedString.substring(0, preparedString.length()-1)+") VALUES (";
        System.out.println(c.getSimpleName());

        for(Field f:fields){
            preparedString = preparedString+"?,";
        }
        preparedString = preparedString.substring(0, preparedString.length()-1)+");";

        PreparedStatement statement = conn.prepareStatement(preparedString);
        for (int i = 1; i<=fields.length; i++ ){
            try {
                System.out.println(fields[i-1].getType());
//                if(fields[i-1].getType().equals("int")) {
//                    statement.setInt(i, (Integer) methodList.get(i - 1).invoke(instance));
//                }
//                else if(fields[i-1].getType().equals("class java.lang.String")){
//                    statement.setString(i, (String) methodList.get(i-1).invoke(instance));
//                }
                if(methodList.get(i-1).invoke(instance) instanceof Integer){
                    statement.setInt(i, (Integer) methodList.get(i-1).invoke(instance));
                } else if(methodList.get(i-1).invoke(instance) instanceof String){
                    statement.setString(i, (String) methodList.get(i-1).invoke(instance));
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } statement.executeUpdate();
        System.out.println("New row was added successfully");
    }
}
