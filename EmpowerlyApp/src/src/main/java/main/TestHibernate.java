// class testing untuk hiberante ORM

package main;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class TestHibernate {
    public static void main(String[] args) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            User newUser = new User("orm_user", "12345");
            session.save(newUser);

            tx.commit();
            System.out.println("User baru berhasil disimpan dengan Hibernate!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, 1); // ambil user dengan id=1
            if (user != null) {
                System.out.println("Username: " + user.getUsername());
            } else {
                System.out.println("Data user tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
