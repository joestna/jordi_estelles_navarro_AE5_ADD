package migracionDatosBiblioteca;

import java.io.FileNotFoundException;
import java.io.IOException;
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

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		
		// Conexion a base de datos con hibernate
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
	
	static void Controlador( Session session, Scanner sc )
	{	
		boolean appContinua = true;
		
		while( appContinua ) 
		{
			System.out.println( "\n> QUE DESEA HACER? <" );
			System.out.println( "1 = Mostrar todos los libros" );
			System.out.println( "2 = Mostrar un libro" );
			System.out.println( "3 = Anyadir libro a biblioteca" );
			System.out.println( "4 = Modificar atributos de un libro" );
			System.out.println( "5 = Borrar un libro" );
			System.out.println( "6 = Cerrar APP" );
			
			String opcion = sc.next();
			
			
			switch( opcion )
			{
				// Mostrar todos los libros
				case "1":
					try
					{
						List<Libro> biblioteca = new ArrayList(); //Diferencia entre list y arraylist??
						biblioteca = session.createQuery("FROM libro").list();
						
						System.out.println("\nLibros mostrados correctamente de la Base de Datos\n");
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
						int id = sc.nextInt();
						Libro libro = (Libro) session.get(Libro.class, id);
						
						System.out.println("\nLibro mostrado correctamente de la Base de Datos\\n");
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
						
						System.out.println("\nLibro anyadido correctamente de la Base de Datos\n");
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
						int id = sc.nextInt();
						Libro libroModificar = (Libro) session.get(Libro.class, id); // almacena la informacion de un libro de la base de datos en el libro
						Libro libroModificado = CrearUnLibro( sc );// Crea un nuevo libro que reescribe al libro en bd
						session.update(libroModificado);
						
						System.out.println("\nLibro actualizado correctamente de la Base de Datos\n");
					}
					//catch especifico para si scanner no lee un int
					catch( Exception e )
					{
						e.printStackTrace();
					}
					
					break;
					
				// Borrar un libro
				case "5":
					try
					{
						int id = sc.nextInt();
						Libro libro = (Libro) session.get(Libro.class, id);
						session.delete(libro);
						
						System.out.println("\nLibro eliminado correctamente de la Base de Datos\n");
					}
					//catch especifico para si scanner no lee un int
					catch( Exception e )
					{
						e.printStackTrace();
					}
					
					break;
				
				// Cerrar la app
				case "6" :
					appContinua = !appContinua;
					System.out.println( "Cerrar APP --> 7" );
					
					break;
					
				default :
					System.out.println("\nValor introducido incorrecto!\n");
			}
		}
	}
	
	static Libro CrearUnLibro( Scanner sc )
	{
		System.out.println( "\nIntroduce el titulo del libro : " );
		String titulo = sc.next();
		
		System.out.println( "\nIntroduce el autor del libro : " );
		String autor = sc.next();
		
		System.out.println( "\nIntroduce el anyo de nacimiento del autor del libro : " );
		String anyoNacimiento = sc.next();
		
		System.out.println( "\nIntroduce el anyo de publicacion del libro : " );
		int anyoPublicacion = sc.nextInt();
		
		System.out.println( "\nIntroduce la editorial del libro : " );
		String editorial = sc.next();
		
		System.out.println( "\nIntroduce el numero de paginas del libro : " );
		int numPaginas = sc.nextInt();
		
		Libro libro = new Libro( titulo, autor, anyoNacimiento, anyoPublicacion, editorial, numPaginas );
		
		return libro;
	}
	
	
	// Metodo : main
	// Parametros : String[] 
	// Funcionalidad : Consultas en bucle para ejecutar funcionalidades
	// Return : void
	public static void prueba(String[] args) throws ClassNotFoundException, FileNotFoundException
	{
		Scanner sc = new Scanner( System.in );
		
		Biblioteca biblioteca = new Biblioteca();
		GestionBD gestorBD = new GestionBD();
		
		boolean continuidadAPP = true;
		
		while( continuidadAPP )
		{
			System.out.println( "\nQue desea hacer? : " );
			System.out.println( "Consultar base de datos (consulta manual) --> 1" );
			System.out.println( "Mostrar un libro --> 2" );
			System.out.println( "Anyadir libro a biblioteca --> 3" );
			System.out.println( "Modificar atributos de un libro --> 4" );
			System.out.println( "Borrar un libro --> 5" );
			System.out.println( "Mostrar todos los libros --> 6" );
			System.out.println( "Cerrar APP --> 7" );
			System.out.print( "> " );
			String respuesta = sc.next();
			System.out.print( "\n" );
			
			switch( respuesta ) 
			{
				case "1" :
					String consulta = gestorBD.GenerarConsulta( sc );
					
					if(consulta.equals("No hay consulta"))
					{
						break;
					}
					
					gestorBD.ConsultarBD( consulta );
					break;
					
				case "2" :					
					try 
					{
						System.out.println( "\nIntroduce el ID del libro a mostrar : " );
						int id = sc.nextInt();
						gestorBD.MostrarInformacionLibro( id );
					}
					catch( Exception e )
					{
						e.printStackTrace();
					}
					
					break;
					
				case "3" :					
					try {
						System.out.println( "\nIntroduce el titulo del libro : " );
						String titulo = sc.next();
						
						System.out.println( "\nIntroduce el autor del libro : " );
						String autor = sc.next();
						
						System.out.println( "\nIntroduce el anyo de nacimiento del autor del libro : " );
						String anyoNacimiento = sc.next();
						
						System.out.println( "\nIntroduce el anyo de publicacion del libro : " );
						int anyoPublicacion = sc.nextInt();
						
						System.out.println( "\nIntroduce la editorial del libro : " );
						String editorial = sc.next();
						
						System.out.println( "\nIntroduce el numero de paginas del libro : " );
						int numPaginas = sc.nextInt();
						
						gestorBD.AnyadirLibroBD( titulo, autor, anyoNacimiento, anyoPublicacion, editorial, numPaginas );
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
					break;
					
				case "4" :
					try {
						System.out.println( "\nIntroduce el id del libro a modificar : " );
						int id = sc.nextInt();
						
						gestorBD.MostrarInformacionLibro( id );
						
						System.out.println( "\nIntroduce el nuevo titulo del libro : " );
						String titulo = sc.next();
						
						System.out.println( "\nIntroduce el nuevo autor del libro : " );
						String autor = sc.next();
						
						System.out.println( "\nIntroduce el nuevo anyo de nacimiento del autor del libro : " );
						String anyoNacimiento = sc.next();
						
						System.out.println( "\nIntroduce el nuevo anyo de publicacion del libro : " );
						int anyoPublicacion = sc.nextInt();
						
						System.out.println( "\nIntroduce la nueva editorial del libro : " );
						String editorial = sc.next();
						
						System.out.println( "\nIntroduce el nuevo numero de paginas del libro : " );
						int numPaginas = sc.nextInt();
						
						gestorBD.ModificarLibroBD( titulo, autor, anyoNacimiento, anyoPublicacion, editorial, numPaginas, id );
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
					break;			
				
				case "5" :
					try 
					{
						System.out.println( "\nIntroduce el id del libro a borrar : " );
						int id  = sc.nextInt();
						
						gestorBD.BorrarLibroBD( id );
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
					break;
				
				case "6" :
					try 
					{
						gestorBD.MostrarInformacionTodosLosLibros();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
					break;
					
				case "7" :
					System.out.println( ">> Nos vemos pronto ;)" );
					continuidadAPP = false;
					break;						

				default :
					System.out.println( ">> Valor introducido no valido." );
					break;				
			}
		}
	}
}
