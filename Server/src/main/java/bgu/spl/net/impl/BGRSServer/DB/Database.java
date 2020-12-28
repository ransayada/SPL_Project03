package bgu.spl.net.impl.BGRSServer.DB;

public class Database {

    private String pathCourses;
	private ConcurrentHashMap<Integer, Course> coursesDB;
	private ConcurrentHashMap<String, User> usersDB;
	private ReadWriteLock readWriteLockCourses;
	private ReadWriteLock readWriteLockUsers;
	private List<Integer> courseOrder;
	


	//to prevent user from creating new Database
	private Database() {
		// TODO: implement - make sure threadSafe singelton?
		pathCourses = this.pathCourses;
		coursesDB = new ConcurrentHashMap<Integer,Course>();
		usersDB = new ConcurrentHashMap<Integer,User>();
		readWriteLockCourses = new readWriteLockCourses;
		readWriteLockUsers = new readWriteLockUsers;
		courseOrder = new List<Integer>; //TODO: initialize 0 or 1?
		
	}

	
	/**
	 * Retrieves the single instance of this class.
	 */
	public static Database getInstance() {
		return singleton;
	}
	
	private Course strToCourse(String line){
		int courseNum;
		String courseName;
		List<Integer> kdamCoursesList;
		int numOfMaxStudents;
		int pointer1 = line.indexOf('|');
		courseNum = line.substring(0,pointer1).intValue();
		line = line.substring(pointer1);
		int pointer2 = line.indexOf('|');
		courseName = line.substring(0,pointer2);
		line = line.substring(pointer2);
		int pointer3 = line.indexOf('|');
		kdamCoursesList = line.substring(0,pointer3); // need to change format to list
		line = line.substring(pointer3);
		numOfMaxStudents = line.intValue();
		return new Course(courseNum,courseName,kdamCoursesList,numOfMaxStudents);
	}
	
	/**
	 * loades the courses from the file path specified 
	 * into the Database, returns true if successful.
	 */
	boolean initialize(String coursesFilePath) {
		// TODO: implement
		Integer courseNum;
		Course course;
		String clone;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(coursesFilePath));
			String line = reader.readLine();
			while (line != null) {
				 course = strToCourse(line);
				 int pointer  = line.indexOf('|');
				 courseNum = line.substring(0, pointer);
				 courseOrder.add(courseNum);
				 coursesDB.putIfAbsent(courseNum,course);
				// read next line
				line = reader.readLine();
			}
			reader.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
    public boolean isAdmin(String userName){//TODO:implement
        return true;
    }
}
