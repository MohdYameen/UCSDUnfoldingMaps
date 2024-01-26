package myTest;

public class InheritanceTest {

	public static void main(String[] args) {
		
	}

}

class Person extends Object{
	private String name;

	public Person(String n){
		super();
		this.name = n;
	}
	public String getName() {
		return name;
	}

	public void setName(String n){
		this.name = n;
	}
}

class Student extends Person {
	private int id;

	public int getId() {
		return id;
	}

	public Student(String n){
		super(n);

		//this.name = n;
	}
}

class Faculty extends Person {
	private String id;

	public Faculty(String n){
		super(n);
	}
	public String getId() {
		return id;
	}
}