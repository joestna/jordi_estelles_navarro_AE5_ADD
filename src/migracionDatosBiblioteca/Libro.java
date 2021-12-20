package migracionDatosBiblioteca;

public class Libro 
{
	public String titulo;
	public String autor;
	public String anyoNacimiento;
	public int anyoPublicacion;
	public String editorial;
	public int numeroPaginas;
	// No creamos un atributo de clase "id" porque de eso se encarga la base de datos
	public int id;
	
	public Libro( String titulo, String autor, String anyoNacimiento, int anyoPublicacion, String editorial, int numeroPaginas )
	{
		this.titulo = titulo;
		this.autor = autor;
		this.anyoNacimiento = anyoNacimiento;
		this.anyoPublicacion = anyoPublicacion;
		this.editorial = editorial;
		this.numeroPaginas = numeroPaginas;
	}
	
	public int getAnyoPublicacion()
	{
		return anyoPublicacion;
	}
	
	public String getTitulo() 
	{
		return titulo;
	}
	
	public String getAutor() 
	{
		return autor;
	}
	
	public String getAnyoNacimiento()
	{
		return anyoNacimiento;
	}
	
	public String getEditorial()
	{
		return editorial;
	}
	
	public int getNumeroPaginas()
	{
		return numeroPaginas;
	}
}
