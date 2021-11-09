package migracionDatosBiblioteca;

import java.util.Scanner;

public class App {

	public static void main(String[] args) 
	{
		Scanner sc = new Scanner( System.in );
		
		Biblioteca biblioteca = new Biblioteca();
		GestionBD gestorBD = new GestionBD();
		
		boolean continuidadAPP = true;
		
		while( continuidadAPP )
		System.out.println( "Que desea hacer? : " );
		System.out.println( "> Actualizar base de datos --> 1" );
		System.out.println( "> Consultar base de datos --> 2" );
		System.out.println( "> Cerrar APP --> 3" );
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
				System.out.println( ">> Nos vemos pronto ;)" );
				continuidadAPP = false;
				break;
				
			default :
				System.out.println( ">> Valor introducido no valido." );
				break;
				
		}
	}
}
