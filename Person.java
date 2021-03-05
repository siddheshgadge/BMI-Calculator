class Person {

	private String name;
	private int age;
	private long phone;
	private String gender;
	private double height;
	private double weight;

	public Person() {}

	public Person(String name, int age, long phone, String gender, double height, double weight){
		this.name = name;
		this.age = age;
		this.phone = phone;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public double getHeight(){
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight(){
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}