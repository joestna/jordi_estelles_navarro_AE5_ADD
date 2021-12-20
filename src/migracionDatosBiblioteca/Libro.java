package migracionDatosBiblioteca;

public class Libro 
{
	private String titulo;
	private String autor;
	private String anyoNacimiento;
	private int anyoPublicacion;
	private String editorial;
	private int numeroPaginas;
	private int id;
	
	public Libro() 
	{
		
	}
	
	public Libro( String titulo, String autor, String anyoNacimiento, int anyoPublicacion, String editorial, int numeroPaginas )
	{
		this.titulo = titulo;
		this.autor = autor;
		this.anyoNacimiento = anyoNacimiento;
		this.anyoPublicacion = anyoPublicacion;
		this.editorial = editorial;
		this.numeroPaginas = numeroPaginas;
	}
	
	public Libro( String titulo, String autor, String anyoNacimiento, int anyoPublicacion, String editorial, int numeroPaginas, int id )
	{
		this.titulo = titulo;
		this.autor = autor;
		this.anyoNacimiento = anyoNacimiento;
		this.anyoPublicacion = anyoPublicacion;
		this.editorial = editorial;
		this.numeroPaginas = numeroPaginas;
		this.id = id;
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
	
	public int getId()
	{
		return id;
	}
	
	public void setId( int id )
	{
		this.id = id;
	}
	
	public void setTitulo( String titulo )
	{
		this.titulo = titulo;
	}
	
	public void setAutor( String autor )
	{
		this.autor = autor;
	}
	
	public void setAnyoNacimiento( String anyoNacimiento )
	{
		this.anyoNacimiento = anyoNacimiento;
	}
	
	public void setAnyoPublicacion( int anyoPublicacion )
	{
		this.anyoPublicacion = anyoPublicacion;
	}
	
	public void setEditorial( String editorial )
	{
		this.editorial = editorial;
	}
	
	public void setNumeroPaginas( int numeroPaginas )
	{
		this.numeroPaginas = numeroPaginas;
	}
	
	public String toString()
	{
		return( "Objeto libro : " + getId() + " " + getTitulo() + " " + getAutor() + " " + getAnyoNacimiento() + " " + getAnyoPublicacion() + " " + getEditorial() + " " + getNumeroPaginas() );
	}
}
