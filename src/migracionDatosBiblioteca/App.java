package migracionDatosBiblioteca;

import java.io.Serializable;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class App {
	
	static boolean continuar = true;

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		
		while( continuar ) 
		{
			Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
			configuration.addClass(Libro.class);
			ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			SessionFactory sessionFactory = configuration.buildSessionFactory(registry);
			
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			// Controlador
			Controlador( session, sc );		
		
			// Finalizar la conexion con base de datos
			session.getTransaction().commit();
			session.close();
		}
		// Conexion a base de datos con hibernate

		System.out.println(" >> FIN << ");
	}
	
	static void Controlador( Session session, Scanner sc )
	{	
		boolean appContinua = true;

		System.out.println( "\n> QUE DESEA HACER? <" );
		System.out.println( "1 = Mostrar todos los libros" );
		System.out.println( "2 = Mostrar un libro" );
		System.out.println( "3 = Anyadir libro a biblioteca" );
		System.out.println( "4 = Modificar atributos de un libro" );
		System.out.println( "5 = Borrar un libro" );
		System.out.println( "6 = Cerrar APP" );
		
		System.out.print("> ");
		String opcion = sc.next();
		
		
		switch( opcion )
		{
			// Mostrar todos los libros
			case "1":
				try
				{
					List<Libro> biblioteca = new ArrayList(); //Diferencia entre list y arraylist??
					biblioteca = session.createQuery("FROM Libro").list();
					
					System.out.println("");
					for( Libro libro : biblioteca )
					{
						System.out.println( libro.toString() );	
					}
					
					System.out.println(">> Libros mostrados correctamente de la Base de Datos\n");
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
				
				break;
				
			// Mostrar un libro
			case "2":
				try
				{
					System.out.print("\nIntroduce el ID del libro : ");
					int id = sc.nextInt();
					
					Libro libro = (Libro) session.get(Libro.class, id);
					
					System.out.println( libro.toString() );						
					System.out.println(">> Libro mostrado correctamente de la Base de Datos\n");
				}				
				catch( Exception e )
				{
					e.printStackTrace();
				}
				
				break;
			
			// Anyadir libro a biblioteca
			case "3":
				try
				{
					Libro libro = CrearUnLibro( sc );
					Serializable id = session.save( libro ); // devuelve el id del objeto que ha creado en la base de datos
					
					System.out.println(">> ID : " + id + " | 1Libro anyadido correctamente de la Base de Datos\n");
				}				
				catch( Exception e )
				{
					e.printStackTrace();
				}
				
				break;
		
			// Modificar atributos de un libro
			case "4":
				try
				{
					System.out.print("\nIntroduce el ID del libro : ");
					int id = sc.nextInt();
					
					Libro libroModificar = (Libro) session.get(Libro.class, id); // almacena la informacion de un libro de la base de datos en el libro
					Libro libroModificado = CrearUnLibro( sc );// Crea un nuevo libro que reescribe al libro en bd
					session.update(libroModificado);
					
					System.out.println(">> Libro actualizado correctamente de la Base de Datos\n");
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
				
				break;
				
			// Borrar un libro
			case "5":
				try
				{
					System.out.print("\nIntroduce el ID del libro : ");
					int id = sc.nextInt();
					
					Libro libro = (Libro) session.get(Libro.class, id);
					session.delete(libro);
					
					System.out.println(">> Libro eliminado correctamente de la Base de Datos\n");
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
				
				break;
			
			// Cerrar la app
			case "6" :
				continuar = false;
				
				break;
				
			default :
				System.out.println(">> Valor introducido incorrecto!\n");
		}
		
		// Pausa la app 2 segundos para que si se lanza alguna exception en la seleccion anterior no rompa el menu
		try
		{
			Thread.sleep(2000);
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	static Libro CrearUnLibro( Scanner sc )
	{
		System.out.print( "\nIntroduce el titulo del libro : " );
		String titulo = sc.next();
		
		System.out.print( "Introduce el autor del libro : " );
		String autor = sc.next();
		
		System.out.print( "Introduce el anyo de nacimiento del autor del libro : " );
		String anyoNacimiento = sc.next();
		
		System.out.print( "Introduce el anyo de publicacion del libro : " );
		int anyoPublicacion = sc.nextInt();
		
		System.out.print( "Introduce la editorial del libro : " );
		String editorial = sc.next();
		
		System.out.print( "Introduce el numero de paginas del libro : " );
		int numPaginas = sc.nextInt();
		
		Libro libro = new Libro( titulo, autor, anyoNacimiento, anyoPublicacion, editorial, numPaginas );
		
		return libro;
	}
}
