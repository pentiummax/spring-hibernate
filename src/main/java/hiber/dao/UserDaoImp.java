package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
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
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      return (User) sessionFactory.getCurrentSession().createQuery("select user " +
              "from User as user " +
              "where user.car.model = '" + model +
              "' and user.car.series = " + series).getSingleResult();
   }
}
