import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main {

    private static EntityManagerFactory entityManagerFactory;

    public static void main(String[] args){
        removerLembretePorID();
    }

    public void inserirLembrete(){
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernatejpa");

        Lembrete lembrete = new Lembrete();
        lembrete.setTitulo("TCC");
        lembrete.setDescricao("Hoje");

        EntityManager em = entityManagerFactory.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(lembrete);
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();

            System.out.println("ERROR INSERT LEMBRETE: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public static void buscarLembrete(){
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernatejpa");
        EntityManager em = entityManagerFactory.createEntityManager();

        List<Lembrete> lembreteList = null;

        try{
            lembreteList = em.createQuery("from Lembrete").getResultList();
        }catch (Exception e){
            System.out.println("ERROR SELECT: " + e.getMessage());
        }finally {
            em.close();
        }

        if(lembreteList != null){
            lembreteList.forEach(System.out::println);
        }
    }

    public static void buscarLembretePorID(){
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernatejpa");

        EntityManager em  = entityManagerFactory.createEntityManager();

        Lembrete lembrete = null;

        try{
            lembrete = em.find(Lembrete.class, 2L);
            System.out.println(lembrete);
        }catch (Exception e){
            System.out.println("ERROR FIND BY ID: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public static void buscarLembretePorTitulo(){
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernatejpa");
        EntityManager em = entityManagerFactory.createEntityManager();

        List<Lembrete> lembreteList = null;

        try{
            lembreteList = em.createQuery("from Lembrete l where l.titulo =: titulo").setParameter("titulo", "TCC").getResultList();
        }catch (Exception e){
            System.out.println("ERROR SELECT: " + e.getMessage());
        }finally {
            em.close();
        }

        if(lembreteList != null){
            lembreteList.forEach(System.out::println);
        }
    }

    public static void atualizarLembrete(){
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernatejpa");
        EntityManager em = entityManagerFactory.createEntityManager();

        Lembrete lembrete = null;

        try{
            lembrete = em.find(Lembrete.class, 1L);

            if(lembrete != null){
                lembrete.setTitulo("Comprar leite");
                lembrete.setDescricao("Amanhã");

                em.getTransaction().begin();
                em.merge(lembrete);
                em.getTransaction().commit();
            }
        } catch (Exception e){
            em.getTransaction().rollback();

            System.out.println("ERROR UPDATE: " + e.getMessage());
        }finally {
            em.close();
        }

    }

    public static void removerLembretePorID(){
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernatejpa");

        EntityManager em = entityManagerFactory.createEntityManager();

        Lembrete lembrete = null;

        try{
            lembrete = em.find(Lembrete.class, 1L);

            em.getTransaction().begin();
            em.remove(lembrete);
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();

            System.out.println("ERROR DELETE: " + e.getMessage());
        }finally {
            em.close();
        }
    }
}
