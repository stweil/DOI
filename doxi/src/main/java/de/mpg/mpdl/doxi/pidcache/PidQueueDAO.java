package de.mpg.mpdl.doxi.pidcache;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;

public class PidQueueDAO {
  private final EntityManager em;

  public PidQueueDAO(EntityManager em) {
    this.em = em;
  }

  public void create(PidQueue pidQueue) {
    this.em.persist(pidQueue);
  }
  
  public void remove(PidQueue pidQueue) {
    this.em.remove(pidQueue);
  }
  
  public PidQueue find(PidID pidID) {
    return this.em.find(PidQueue.class, pidID.getIdAsString());
  }
  
  public PidQueue findByUrl(URI url) {
    final String query = "SELECT p FROM pid_queue p where p.url= ?1";
    
    return this.em.createQuery(query, PidQueue.class).setParameter(1, url.toString()).getSingleResult();
  }
  
  public PidQueue getFirst() {
    final String query = "SELECT p FROM pid_queue p ORDER BY p.created DESC";
    
    return this.em.createQuery(query, PidQueue.class).getSingleResult();
  }
  
  public List<PidQueue> getFirst(int anz) {
    final String query = "SELECT p FROM pid_queue p ORDER BY p.created DESC";
    
    return this.em.createQuery(query, PidQueue.class).setMaxResults(anz).getResultList();
  }
  
  public int getSize() {
    String query = "SELECT count(*) FROM pid_cache";
    
    return this.em.createQuery(query, Integer.class).getSingleResult();
  }
}
