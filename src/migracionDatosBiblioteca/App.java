package migracionDatosBiblioteca;

import java.util.Scanner;

public class App {

	public static void main(String[] args) throws ClassNotFoundException
	{
		Scanner sc = new Scanner( System.in );
		
		Biblioteca biblioteca = new Biblioteca();
		GestionBD gestorBD = new GestionBD();
		
		boolean continuidadAPP = true;
		
		while( continuidadAPP )
		{
			System.out.println( "\nQue desea hacer? : " );
			System.out.println( "Actualizar base de datos --> 1" );
			System.out.println( "Consultar base de datos --> 2" );
			System.out.println( "Consultas automaticas --> 3" );
			System.out.println( "Cerrar APP --> 4" );
			System.out.print( "> " );
			String respuesta = sc.next();
			
			switch( respuesta )
			{
				case "1" :
					biblioteca.setAlmacenLibros( gestorBD.AnalizarCSV() );				
					gestorBD.MigracionABDMySQL( biblioteca.getAlmacenLibros() );
					break;
					
				case "2" :
					gestorBD.ConsultarBD( sc );
					break;
					
				case "3" :
					//gestorBD.ConsultasAutomaticas();
					break;
					
				case "4" :
					System.out.println( ">> Nos vemos pronto ;)" );
					continuidadAPP = false;
					break;					
					
				default :
					
					//TESTING
//					for( Libro libro : gestorBD.AnalizarCSV() )
//					{
//						System.out.println( libro.getTitulo() );
//						System.out.println( libro.getAutor() );
//						System.out.println( libro.getAnyoNacimiento() );
//						System.out.println( libro.getAnyoPublicacion() );
//						System.out.println( libro.getEditorial() );
//						System.out.println( libro.getNumeroPaginas() );
//					}

					System.out.println( ">> Valor introducido no valido." );
					break;				
			}
		}
	}
}
