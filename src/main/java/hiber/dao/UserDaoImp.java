package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      Session session = sessionFactory.openSession();
      final Transaction transaction = session.getTransaction();
      transaction.begin();
      try {
         session.persist(user);
         transaction.commit();
      } catch (Exception e) {
         e.printStackTrace();
         transaction.rollback();
      } finally {
         session.close();
      }
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      try (Session session = sessionFactory.openSession()) {
         TypedQuery<User> query = session.createQuery("from User");
         return query.getResultList();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return Collections.EMPTY_LIST;
   }

   @Override
   public User getUserByCar(String model, int series) {
      try (Session session = sessionFactory.openSession()) {
         return (User) session.createQuery("select user " +
                 "from User as user " +
                 "where user.car.model = '" + model +
                 "' and user.car.series = " + series).getSingleResult();

      } catch (Exception e) {
         e.printStackTrace();
      }
      return null;
   }
}
