package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MenuBarController{
	public static int SIDCounter = 0, CIDCounter = 0, EIDCounter = 0;
	public static int studentRecordNumberEditing = 0, courseRecordNumberEditing = 0, enrollmentRecordNumberEditing = 0;
	@FXML
	private MenuItem addStudentButton,addCourseButton,addEnrollmentButton;
	@FXML
	private MenuItem searchStudentButton,searchCourseButton,searchEnrollmentButton;
	@FXML
	private MenuItem manageGradesButton;
	@FXML
	private Label MenuLabel;
	@FXML
	private VBox addStudentVBOX, postAddStudentVBOX, editStudentVBOX, displayStudentVBOX, searchStudentVBOX, addCourseVBOX;
	@FXML
	private VBox postAddCourseVBOX, editCourseVBOX, displayCourseVBOX, searchCourseVBOX;
	@FXML
	private TextField addStudentAddressTextField, addStudentFirstNameTextField, addStudentLastNameTextField, addStudentStateTextField, addStudentCityTextField;
	@FXML
	private Button createStudentButton, cancelButton, addNewStudentButton, postAddStudentEditButton;
	@FXML
	private Label postAddStudentID, postAddStudentFirstName, postAddStudentLastName, postAddStudentAddress, postAddStudentCity, postAddStudentState;
	@FXML
	private TextField editStudentFirstName, editStudentLastName, editStudentAddress, editStudentCity, editStudentState;
	@FXML
	private Label displayStudentID, displayStudentFirstName, displayStudentLastName, displayStudentAddress, displayStudentCity, displayStudentState;
	@FXML
	private TextField studentSearchID;
	@FXML
	private Button studentSearchButton;
	@FXML
	private ChoiceBox<String> addCourseInstructorChoiceBox, addCourseDepartmentChoiceBox;
	@FXML 
	private TextField addCourseNumber, addCourseName;
	@FXML
	private Label postAddCourseID, postAddCourseNumber, postAddCourseName, postAddCourseInstructor, postAddCourseDepartment;
	@FXML
	private TextField editCourseNumber, editCourseName;
	@FXML
	private ChoiceBox<String> editCourseInstructorChoiceBox, editCourseDepartmentChoiceBox;
	@FXML
	private TextField courseSearchID;
	@FXML
	private Label displayCourseID, displayCourseNumber, displayCourseName, displayCourseInstructor, displayCourseDepartment;
	@FXML
	private VBox searchStudentCreateEnrollmentVBOX;
	@FXML
	private HBox createEnrollmentHBOX;
	@FXML
	private Label createEnrollmentDisplayStudentID, createEnrollmentDisplayStudentFirstName, createEnrollmentDisplayStudentLastName;
	@FXML
	private Label createEnrollmentDisplayStudentAddress, createEnrollmentDisplayStudentCity, createEnrollmentDisplayStudentState;
	@FXML
	private ChoiceBox<String> createEnrollmentCourseChoiceBox, createEnrollmentYearChoiceBox, createEnrollmentSemesterChoiceBox;
	@FXML
	private TextField enrollmentStudentSearchID;
	@FXML
	private VBox postAddEnrollmentVBOX;
	@FXML
	private Label postAddEnrollmentEID, postAddEnrollmentCID, postAddEnrollmentSID, postAddEnrollmentYear, postAddEnrollmentSemester, postAddEnrollmentGrade;
	@FXML
	private HBox editEnrollmentHBOX;
	@FXML
	private Label editEnrollmentDisplayStudentID, editEnrollmentDisplayStudentFirstName, editEnrollmentDisplayStudentLastName;
	@FXML
	private Label editEnrollmentDisplayStudentAddress, editEnrollmentDisplayStudentCity, editEnrollmentDisplayStudentState;
	@FXML
	private ChoiceBox<String> editEnrollmentCourseChoiceBox, editEnrollmentYearChoiceBox, editEnrollmentSemesterChoiceBox;
	@FXML
	private TextField searchEnrollmentStudentID;
	@FXML
	private ChoiceBox<String> searchEnrollmentYear;
	@FXML
	private ChoiceBox<String> searchEnrollmentSemester;
	@FXML
	private VBox displayEnrollmentVBOX;
	@FXML
	private VBox searchEnrollmentGradeManagementVBOX, gradeManagementEditVBOX, searchEnrollmentVBOX;
	@FXML
	private Label gradeManagementDisplayEID, gradeManagementDisplaySID, gradeManagementDisplayCID, gradeManagementDisplayYear;
	@FXML
	private Label gradeManagementDisplaySemester;
	@FXML
	private ChoiceBox<String> gradeManagementGradeChoiceBox;
	@FXML
	private TextField enrollmentSearchID;
	@FXML
	private Label displayEnrollmentEID, displayEnrollmentCID, displayEnrollmentSID, displayEnrollmentYear, displayEnrollmentSemester;
	@FXML
	private Label displayEnrollmentGrade;
	@FXML
	private TextField gradeManagementStudentID;
	@FXML
	private ChoiceBox<String> gradeManagementYear, gradeManagementSemester;
	@FXML
	private Label gradeManagementSaveIndicator;
	@FXML
	private ChoiceBox<String> searchReportCourseChoiceBox, searchReportYearChoiceBox;
	@FXML
	private Button reportSearchButton;
	@FXML
	private VBox searchReportVBOX;
	@FXML
	private ListView<String> listOfStudentInfo = new ListView<String>();
	@FXML
	private VBox displayReportVBOX;
	
	private int searchStudentRequestedID = 0, searchCourseRequestedID = 0, searchEnrollmentRequestedID = 0;
	private int createEnrollmentRequestedStudentID = 0;
	private boolean studentJustSearched = false, courseJustSearched = false, enrollmentJustSearched = false;
	private boolean gradeManagementJustSearched = false;
	private Enrollment searchedEnrollment;
	private List<Integer> listOfReportEnrollmentIds;
	
	public void initialize() throws IOException{
		LOG("Controller main");
		studentFile = new StudentFile("StudentFile.dat");
		studentFile.truncate(); // for testing reasons, delete for final result
		
		courseFile = new CourseFile("CourseFile.dat");
		courseFile.truncate();
		
		enrollmentFile = new EnrollmentFile("EnrollmentFile.dat");
		enrollmentFile.truncate();
		
		setCounters();
		
		listOfStudentInfo.getItems();
		
		addCourseInstructorChoiceBox.getItems().removeAll(addCourseInstructorChoiceBox.getItems());
		addCourseInstructorChoiceBox.getItems().addAll("Kim", "Jones", "Java", "Pete", "Scott");
		addCourseDepartmentChoiceBox.getItems().removeAll(addCourseDepartmentChoiceBox.getItems());
		addCourseDepartmentChoiceBox.getItems().addAll("English", "Science", "Biology", "Math", "Chemistry");
		
		makeAllInvisible();
		MenuLabel.setVisible(true);
	}
	
	private static void LOG(String str) {System.out.println(str);}
	private static void LOG(int number) {System.out.println(number);}
	
	private void setCounters() throws IOException{
		SIDCounter = (int)studentFile.getNumberOfRecords();
		CIDCounter = (int)courseFile.getNumberOfRecords();
		EIDCounter = (int)enrollmentFile.getNumberOfRecords();
	}
	
	public void addStudentButtonListener() {
		LOG("Add Student");
		studentJustSearched = false;
		courseJustSearched = false;
		gradeManagementJustSearched = false;
		enrollmentJustSearched = false;
		makeAllInvisible();
		addStudentVBOX.setVisible(true);
	}
	
	public void addCourseButtonListener() {
		LOG("Add Class");
		studentJustSearched = false;
		courseJustSearched = false;
		gradeManagementJustSearched = false;
		enrollmentJustSearched = false;
		makeAllInvisible();
		addCourseVBOX.setVisible(true);
	}
	
	public void addEnrollmentButtonListener() {
		LOG("Add Enrollment");
		studentJustSearched = false;
		courseJustSearched = false;
		gradeManagementJustSearched = false;
		enrollmentJustSearched = false;
		makeAllInvisible();
		searchStudentCreateEnrollmentVBOX.setVisible(true);
	}
	
	public void searchStudentButtonListener() throws IOException {
		LOG("Search Student");
		studentJustSearched = false;
		courseJustSearched = false;
		gradeManagementJustSearched = false;
		enrollmentJustSearched = false;
		makeAllInvisible();
		searchStudentVBOX.setVisible(true);
	}
	
	public void searchCourseButtonListener() {
		LOG("Search Class");
		studentJustSearched = false;
		courseJustSearched = false;
		enrollmentJustSearched = false;
		gradeManagementJustSearched = false;
		MenuLabel.setText("Search Class");
		makeAllInvisible();
		searchCourseVBOX.setVisible(true);
	}
	
	public void searchEnrollmentButtonListener() {
		LOG("Search Enrollment");
		studentJustSearched = false;
		courseJustSearched = false;
		gradeManagementJustSearched = false;
		enrollmentJustSearched = false;
		MenuLabel.setText("Search Enrollment");
		makeAllInvisible();
		searchEnrollmentVBOX.setVisible(true);
	}
	
	public void manageGradesButtonListener() {
		LOG("Manage Grades");
		studentJustSearched = false;
		gradeManagementJustSearched = false;
		courseJustSearched = false;
		enrollmentJustSearched = false;
		MenuLabel.setText("Manage Grades");
		makeAllInvisible();
		searchEnrollmentGradeManagementVBOX.setVisible(true);
		if(EIDCounter == 0)
			errorMessage("No enrollments have been made");
		else {
			gradeManagementYear.getItems().removeAll(gradeManagementYear.getItems());
			gradeManagementYear.getItems().addAll("2019", "2020", "2021", "2022");
			gradeManagementSemester.getItems().removeAll(createEnrollmentSemesterChoiceBox.getItems());
			gradeManagementSemester.getItems().addAll("Spring", "Summer", "Fall", "Winter");
		}
	}
	
	public void reportButtonListener() throws IOException{
		LOG("Report");
		studentJustSearched = false;
		gradeManagementJustSearched = false;
		courseJustSearched = false;
		enrollmentJustSearched = false;
		MenuLabel.setText("Report");
		if(EIDCounter == 0) {
			errorMessage("No enrollments have been made");
		}
		else {
			makeAllInvisible();
			searchReportVBOX.setVisible(true);
			searchReportCourseChoiceBox.getItems().removeAll(searchReportCourseChoiceBox.getItems());
			
			long numberOfCourses = courseFile.getNumberOfRecords();
			
			for(long i = 1; i <= numberOfCourses; i++) {
				int id = courseFile.readSelectedCourse(i).getId();
				String num = courseFile.readSelectedCourse(i).getNum();
				String option = String.valueOf(id) + "   " + num;
				
				searchReportCourseChoiceBox.getItems().add(option);
			}
			searchReportYearChoiceBox.getItems().removeAll(searchReportYearChoiceBox.getItems());
			searchReportYearChoiceBox.getItems().addAll("2019","2020","2021","2022");
		}
		
	}
	
	public void cancelButtonListener() {
		makeAllInvisible();
		studentJustSearched = false;
		courseJustSearched = false;
		gradeManagementJustSearched = false;
		enrollmentJustSearched = false;
		MenuLabel.setVisible(true);
		MenuLabel.setText("Menu");
		wipeStudentFormInfo();
		wipeCourseFormInfo();
	}
	
	// Student
	public void createStudentButtonListener() throws IOException{
		LOG("Create Student");
		SIDCounter++;
		String firstName,lastName,address,city,state;
		firstName = addStudentFirstNameTextField.getText();
		lastName = addStudentLastNameTextField.getText();
		address = addStudentAddressTextField.getText();
		city = addStudentCityTextField.getText();
		state = addStudentStateTextField.getText();
		
		Student student = new Student(SIDCounter,firstName,lastName,address,city,state);
		studentFile.writeStudentInfo(student);
		wipeStudentFormInfo();
		postAddStudent();
	}
	
	public void studentSearchButtonListener() throws IOException{
		searchStudentRequestedID = Integer.valueOf(studentSearchID.getText());
		if(SIDCounter == 0) {
			errorMessage("No Students Have Been Created");
		}
		else if(searchStudentRequestedID < 1 || searchStudentRequestedID > SIDCounter){
			errorMessage("Invalid Search");
		}
		else {
			studentJustSearched = true;
			displaySearchedStudent(searchStudentRequestedID);
		}
	}
	
	private void errorMessage(String error) {
		makeAllInvisible();
		MenuLabel.setVisible(true);
		MenuLabel.setText(error);
	}
	
	private void postAddStudent() throws IOException{
		LOG("Post add student");
		
		makeAllInvisible();
		postAddStudentVBOX.setVisible(true);
		
		Student student = studentFile.readSelectedStudent(SIDCounter);
		postAddStudentID.setText(String.valueOf(student.getId()));
		postAddStudentFirstName.setText(student.getFirstName());
		postAddStudentLastName.setText(student.getLastName());
		postAddStudentAddress.setText(student.getAddress());
		postAddStudentCity.setText(student.getCity());
		postAddStudentState.setText(student.getState());
	}
	
	private void wipeStudentFormInfo() {
		addStudentFirstNameTextField.setText("");
		addStudentLastNameTextField.setText("");
		addStudentAddressTextField.setText("");
		addStudentCityTextField.setText("");
		addStudentStateTextField.setText("");
	}
	
	public void postAddStudentEditButtonListener() throws IOException{
		makeAllInvisible();
		editStudentVBOX.setVisible(true);
		studentRecordNumberEditing = SIDCounter;
		Student student = studentFile.readSelectedStudent(studentRecordNumberEditing);
		editStudentFirstName.setText(student.getFirstName());
		editStudentLastName.setText(student.getLastName());
		editStudentAddress.setText(student.getAddress());
		editStudentCity.setText(student.getCity());
		editStudentState.setText(student.getState());
	}
	
	public void editStudentSaveChangesButtonListener() throws IOException{
		String firstName = editStudentFirstName.getText();
		String lastName = editStudentLastName.getText();
		String address = editStudentAddress.getText();
		String city = editStudentCity.getText();
		String state = editStudentState.getText();
		
		Student student = new Student(studentRecordNumberEditing, firstName, lastName, address,city,state);
		
		System.out.println(studentRecordNumberEditing);
		System.out.println(student.getState() + student.getId());
		studentFile.writeSelectedStudent(student, studentRecordNumberEditing);
		studentFile.reset();
		displaySearchedStudent(studentRecordNumberEditing);
	}
	
	private void displaySearchedStudent(int recordNumber) throws IOException{
		System.out.println(recordNumber);
		Student student = studentFile.readSelectedStudent(recordNumber);
		
		makeAllInvisible();
		displayStudentVBOX.setVisible(true);
		
		displayStudentID.setText(String.valueOf(student.getId()));
		displayStudentFirstName.setText(student.getFirstName());
		displayStudentLastName.setText(student.getLastName());
		displayStudentAddress.setText(student.getAddress());
		displayStudentCity.setText(student.getCity());
		displayStudentState.setText(student.getState());
	}
	
	public void displayStudentEditButtonListener() throws IOException{
		makeAllInvisible();
		editStudentVBOX.setVisible(true);

		System.out.println(studentJustSearched + " " + studentRecordNumberEditing + " " + searchStudentRequestedID);
		
		if(searchStudentRequestedID == 0)
			studentRecordNumberEditing = SIDCounter;
		else if(studentJustSearched == false)
			studentRecordNumberEditing = SIDCounter;
		else 
			studentRecordNumberEditing = searchStudentRequestedID;
		
		System.out.println(studentJustSearched + " " + studentRecordNumberEditing + " " + searchStudentRequestedID);
		
		Student student = studentFile.readSelectedStudent(studentRecordNumberEditing);
		
		editStudentFirstName.setText(student.getFirstName());
		editStudentLastName.setText(student.getLastName());
		editStudentAddress.setText(student.getAddress());
		editStudentCity.setText(student.getCity());
		editStudentState.setText(student.getState());
	}
	
	/////////////
	// course
	public void createCourseButtonListener() throws IOException{
		LOG("Creating course");
		CIDCounter++;
		String courseNumber = addCourseNumber.getText();
		String courseName = addCourseName.getText();
		String courseInstructor = addCourseInstructorChoiceBox.getValue();
		String courseDepartment = addCourseDepartmentChoiceBox.getValue();
		
		Course course = new Course(CIDCounter, courseNumber, courseName, courseInstructor, courseDepartment);
		courseFile.writeCourseInfo(course);
		wipeCourseFormInfo();
		postAddCourse();
	}
	
	private void postAddCourse() throws IOException{
		makeAllInvisible();
		postAddCourseVBOX.setVisible(true);
		
		Course course = courseFile.readSelectedCourse(CIDCounter);
		postAddCourseID.setText(String.valueOf(course.getId()));
		postAddCourseNumber.setText(course.getNum());
		postAddCourseName.setText(course.getName());
		postAddCourseInstructor.setText(course.getInstruct());
		postAddCourseDepartment.setText(course.getDepartment());
	}
	
	private void wipeCourseFormInfo() {
		addCourseNumber.setText("");
		addCourseName.setText("");
		addCourseInstructorChoiceBox.setValue(null);
		addCourseDepartmentChoiceBox.setValue(null);
	}
	
	public void postAddCourseEditButtonListener() throws IOException{
		makeAllInvisible();
		editCourseVBOX.setVisible(true);
		courseRecordNumberEditing = CIDCounter;
		Course course = courseFile.readSelectedCourse(courseRecordNumberEditing);
		
		editCourseNumber.setText(course.getNum());
		editCourseName.setText(course.getName());
		editCourseInstructorChoiceBox.getItems().removeAll(addCourseInstructorChoiceBox.getItems());
		editCourseInstructorChoiceBox.getItems().addAll("Kim", "Jones", "Java", "Pete", "Scott");
		editCourseInstructorChoiceBox.setValue(course.getInstruct());
		editCourseDepartmentChoiceBox.getItems().removeAll(addCourseDepartmentChoiceBox.getItems());
		editCourseDepartmentChoiceBox.setValue(course.getDepartment());
		editCourseDepartmentChoiceBox.getItems().addAll("English", "Science", "Biology", "Math", "Chemistry");
	}
	
	public void editCourseSaveChangesButton() throws IOException{
		String number = editCourseNumber.getText();
		String name = editCourseName.getText();
		String instructor = editCourseInstructorChoiceBox.getValue();
		String department = editCourseDepartmentChoiceBox.getValue();
		
		Course course = new Course(courseRecordNumberEditing, number, name, instructor, department);
		
		courseFile.writeSelectedCourseInfo(course, courseRecordNumberEditing);
		displaySearchedCourse(courseRecordNumberEditing);
	}
	
	public void courseSearchButtonListener() throws IOException{
		searchCourseRequestedID = Integer.valueOf(courseSearchID.getText());
		if(CIDCounter == 0)
			errorMessage("No courses have been created");
		else if(searchCourseRequestedID < 1 || searchCourseRequestedID > CIDCounter)
			errorMessage("Invalid Search");
		else {
			courseJustSearched = true;
			displaySearchedCourse(searchCourseRequestedID);
		}
			
	}
	
	private void displaySearchedCourse(int recordNumber) throws IOException{
		Course course = courseFile.readSelectedCourse(recordNumber);
		
		makeAllInvisible();
		displayCourseVBOX.setVisible(true);
		
		displayCourseID.setText(String.valueOf(course.getId()));
		displayCourseNumber.setText(course.getNum());
		displayCourseName.setText(course.getName());
		displayCourseInstructor.setText(course.getInstruct());
		displayCourseDepartment.setText(course.getDepartment());
	}
	
	public void displayCourseEditButtonListener() throws IOException{
		makeAllInvisible();
		editCourseVBOX.setVisible(true);
		
		if(searchCourseRequestedID == 0)
			courseRecordNumberEditing = CIDCounter;
		else if(!courseJustSearched)
			courseRecordNumberEditing = CIDCounter;
		else
			courseRecordNumberEditing = searchCourseRequestedID;
		
		Course course = courseFile.readSelectedCourse(courseRecordNumberEditing);
		
		editCourseNumber.setText(course.getNum());
		editCourseName.setText(course.getName());
		editCourseInstructorChoiceBox.getItems().removeAll(editCourseInstructorChoiceBox.getItems());
		editCourseInstructorChoiceBox.getItems().addAll("Kim", "Jones", "Java", "Pete", "Scott");
		editCourseInstructorChoiceBox.setValue(course.getInstruct());
		editCourseDepartmentChoiceBox.getItems().removeAll(editCourseDepartmentChoiceBox.getItems());
		editCourseDepartmentChoiceBox.setValue(course.getDepartment());
		editCourseDepartmentChoiceBox.getItems().addAll("English", "Science", "Biology", "Math", "Chemistry");
	}
	//////
	// enrollment
	public void createEnrollmentStudentSearchButtonListener() throws IOException{
		createEnrollmentRequestedStudentID = Integer.valueOf(enrollmentStudentSearchID.getText());
		if(SIDCounter == 0) 
			errorMessage("No students have been created");
		else if(createEnrollmentRequestedStudentID > SIDCounter)
			errorMessage("Invalid Student Search");
		else if(CIDCounter == 0)
			errorMessage("No courses have been created");
		else
			enrollmentCanBeCreated();
	}
	
	private void enrollmentCanBeCreated() throws IOException{
		makeAllInvisible();
		createEnrollmentHBOX.setVisible(true);
		
		Student student = studentFile.readSelectedStudent(createEnrollmentRequestedStudentID);
		
		createEnrollmentDisplayStudentID.setText(String.valueOf(student.getId()));
		createEnrollmentDisplayStudentFirstName.setText(student.getFirstName());
		createEnrollmentDisplayStudentLastName.setText(student.getLastName());
		createEnrollmentDisplayStudentAddress.setText(student.getAddress());
		createEnrollmentDisplayStudentCity.setText(student.getCity());
		createEnrollmentDisplayStudentState.setText(student.getState());
		
		createEnrollmentYearChoiceBox.getItems().removeAll(createEnrollmentYearChoiceBox.getItems());
		createEnrollmentYearChoiceBox.getItems().addAll("2019", "2020", "2021", "2022");
		createEnrollmentSemesterChoiceBox.getItems().removeAll(createEnrollmentSemesterChoiceBox.getItems());
		createEnrollmentSemesterChoiceBox.getItems().addAll("Spring", "Summer", "Fall", "Winter");
		addAllCourseEnrollmentOptions();
		
	}
	
	private void addAllCourseEnrollmentOptions() throws IOException {
		createEnrollmentCourseChoiceBox.getItems().removeAll(createEnrollmentCourseChoiceBox.getItems());
		
		long numberOfCourses = courseFile.getNumberOfRecords();
		
		for(long i = 1; i <= numberOfCourses; i++) {
			int id = courseFile.readSelectedCourse(i).getId();
			String num = courseFile.readSelectedCourse(i).getNum();
			String option = String.valueOf(id) + "   " + num;
			
			createEnrollmentCourseChoiceBox.getItems().add(option);
		}
	}
	
	public void createEnrollmentButtonListener() throws IOException{
		LOG("Create enrollment");
		// enrollment id, course id, student id, year, semester, grade
		EIDCounter++;
		int courseID = extractID(createEnrollmentCourseChoiceBox.getValue());
		int studentID = Integer.valueOf(createEnrollmentDisplayStudentID.getText());
		int enrollmentID = EIDCounter;
		String semester = createEnrollmentSemesterChoiceBox.getValue();
		String year = createEnrollmentYearChoiceBox.getValue();
		
		System.out.println("Course: " + courseID + ", Student: " + studentID);
		
		Enrollment enrollment = new Enrollment(enrollmentID, courseID, studentID, year, semester);
		
		System.out.println("Course: " + enrollment.getCourseId() + ", Student: " + enrollment.getStudentId());
		
		enrollmentFile.writeEnrollmentInfo(enrollment);
		postAddEnrollment();
	}
	
	private void postAddEnrollment() throws IOException{
		makeAllInvisible();
		postAddEnrollmentVBOX.setVisible(true);
		
		Enrollment enrollment = enrollmentFile.readSelectedEnrollment(EIDCounter);
		System.out.println("Course: " + enrollment.getCourseId() + ", Enrollment Course: " + String.valueOf(enrollment.getCourseId()));
		postAddEnrollmentEID.setText(String.valueOf(enrollment.getEnrollmentId()));
		postAddEnrollmentCID.setText(String.valueOf(enrollment.getCourseId()));
		System.out.println("Displayed: " + displayEnrollmentCID.getText());
		postAddEnrollmentSID.setText(String.valueOf(enrollment.getStudentId()));
		postAddEnrollmentYear.setText(enrollment.getYear());
		postAddEnrollmentSemester.setText(enrollment.getSemester());
		postAddEnrollmentGrade.setText(enrollment.getGrade());
	}
	
	/// HAVENT DEBUGGED
	// LEFT OFF HERE
	// NEXT THING TO DO IS TO CREATE THE SEARCH STUDENT ID FOR SEARCH ENROLLMENT
	public void postAddEnrollmentEditButtonListener() throws IOException{
		makeAllInvisible();
		editEnrollmentHBOX.setVisible(true);
		enrollmentRecordNumberEditing = EIDCounter;
		Enrollment enrollment = enrollmentFile.readSelectedEnrollment(enrollmentRecordNumberEditing);
		Student student = studentFile.readSelectedStudent(enrollment.getStudentId());
		
		editEnrollmentDisplayStudentID.setText(String.valueOf(student.getId()));
		editEnrollmentDisplayStudentFirstName.setText(student.getFirstName());
		editEnrollmentDisplayStudentLastName.setText(student.getLastName());
		editEnrollmentDisplayStudentAddress.setText(student.getAddress());
		editEnrollmentDisplayStudentCity.setText(student.getCity());
		editEnrollmentDisplayStudentState.setText(student.getState());
		
		editEnrollmentYearChoiceBox.getItems().removeAll(createEnrollmentYearChoiceBox.getItems());
		editEnrollmentYearChoiceBox.getItems().addAll("2019", "2020", "2021", "2022");
		editEnrollmentYearChoiceBox.setValue(enrollment.getYear());
		editEnrollmentSemesterChoiceBox.getItems().removeAll(createEnrollmentSemesterChoiceBox.getItems());
		editEnrollmentSemesterChoiceBox.getItems().addAll("Spring", "Summer", "Fall", "Winter");
		editEnrollmentSemesterChoiceBox.setValue(enrollment.getSemester());
		setUpEditForEnrollmentCourse(enrollment.getCourseId());
	}
	
	public void gradeManagementSearchButtonListener() throws IOException{
		String studentID = gradeManagementStudentID.getText();
		String year = gradeManagementYear.getValue();
		String semester = gradeManagementSemester.getValue();
		
		if(EIDCounter == 0) {
			errorMessage("No enrollments created");
			return;
		}
		
		if(findEnrollment(studentID, year, semester)) {
			gradeManagementJustSearched = true;
			displaySearchedStudentGM();
		}
		else {
			errorMessage("Invalid search");
		}
	}
	
	private void displaySearchedStudentGM() throws IOException{
		makeAllInvisible();
		gradeManagementEditVBOX.setVisible(true);
		Enrollment enrollment = searchedEnrollment;
		gradeManagementDisplayEID.setText(String.valueOf(enrollment.getEnrollmentId()));
		gradeManagementDisplayCID.setText(String.valueOf(enrollment.getCourseId()));
		gradeManagementDisplaySID.setText(String.valueOf(enrollment.getStudentId()));
		gradeManagementDisplayYear.setText(enrollment.getYear());
		gradeManagementDisplaySemester.setText(enrollment.getSemester());
		gradeManagementGradeChoiceBox.getItems().removeAll(gradeManagementGradeChoiceBox.getItems());
		gradeManagementGradeChoiceBox.getItems().addAll("A", "B", "C", "D", "F");
		gradeManagementGradeChoiceBox.setValue(enrollment.getGrade());
		gradeManagementSaveIndicator.setText("");
	}
	
	private boolean findEnrollment(String SID, String year, String semester) throws IOException{
		boolean enrollmentFound = true;
		
		for(int i = 1; i <= EIDCounter; i++) {
			Enrollment dummy = enrollmentFile.readSelectedEnrollment(i);
			System.out.println(Integer.valueOf(SID) + " " + dummy.getStudentId());
			System.out.println(year + " " + dummy.getYear());
			System.out.println(semester + " " + dummy.getSemester());
			
			if(Integer.valueOf(SID) == dummy.getStudentId()) {
				if(year.equalsIgnoreCase(dummy.getYear().trim())){
					if(semester.equalsIgnoreCase(dummy.getSemester().trim())) {
						enrollmentFound = true;
						searchedEnrollment = dummy;
						break;
					}
					else {
						System.out.println("No semester");
						enrollmentFound = false;
					}
				}
				else {
					System.out.println("No year");
					enrollmentFound = false;
				}
			}
			else {
				System.out.println("No SID");
				enrollmentFound = false;
			}
		}
		return enrollmentFound;
	}
	
	private boolean findEnrollment(int course, String year) throws IOException{
		boolean enrollmentFound = true;
		
		for(int i = 1; i <= EIDCounter; i++) {
			Enrollment dummy = enrollmentFile.readSelectedEnrollment(i);
			
			if(year.equalsIgnoreCase(dummy.getYear().trim())){
				if(course == dummy.getCourseId()) {
					enrollmentFound = true;
					searchedEnrollment = dummy;
					break;
				}
				else {
					System.out.println("No semester");
					enrollmentFound = false;
				}
			}
			else {
				System.out.println("No year");
				enrollmentFound = false;
			}
		}
	
		return enrollmentFound;
	}
	
	private List<Integer> findEnrollmentIDs(int course, String year) throws IOException{
		List<Integer> ids = new ArrayList<Integer>();
		
		for(int i = 1; i <= EIDCounter; i++) {
			Enrollment dummy = enrollmentFile.readSelectedEnrollment(i);
			
			if(course == dummy.getCourseId()) {
				if(year.equalsIgnoreCase(dummy.getYear().trim())) {
					ids.add(dummy.getEnrollmentId());
				}
			}
		}
		
		return ids;
	}
	
	public void gradeManagementSaveChangesButtonListener() throws IOException{
		int enrollmentID = Integer.valueOf(gradeManagementDisplayEID.getText());
		Enrollment enrollment = enrollmentFile.readSelectedEnrollment(enrollmentID);
		enrollment.setGrade(gradeManagementGradeChoiceBox.getValue());
		enrollmentFile.writeSelectedEnrollmentInfo(enrollment, enrollmentID);
		gradeManagementSaveIndicator.setText("Grade Saved: " + enrollmentFile.readSelectedEnrollment(enrollmentID).getGrade());
	}
	
	public void editEnrollmentSaveChangesButtonListener() throws IOException{
		int courseID = extractID(editEnrollmentCourseChoiceBox.getValue());
		int studentID = Integer.valueOf(editEnrollmentDisplayStudentID.getText());
		int enrollmentID = enrollmentRecordNumberEditing;
		String semester = editEnrollmentSemesterChoiceBox.getValue();
		String year = editEnrollmentYearChoiceBox.getValue();
		
		Enrollment enrollment = new Enrollment(enrollmentID, courseID, studentID, year, semester);
		
		enrollmentFile.writeSelectedEnrollmentInfo(enrollment, enrollmentRecordNumberEditing);
		displaySearchedEnrollment(enrollmentRecordNumberEditing);
	}
	
	public void displayEnrollmentEditButtonListener() throws IOException{
		makeAllInvisible();
		editEnrollmentHBOX.setVisible(true);
		
		if(searchEnrollmentRequestedID == 0)
			enrollmentRecordNumberEditing = EIDCounter;
		else if(!enrollmentJustSearched)
			enrollmentRecordNumberEditing = EIDCounter;
		else
			enrollmentRecordNumberEditing = searchEnrollmentRequestedID;
		
		Enrollment enrollment = enrollmentFile.readSelectedEnrollment(enrollmentRecordNumberEditing);
		Student student = studentFile.readSelectedStudent(enrollment.getStudentId());
		
		editEnrollmentDisplayStudentID.setText(String.valueOf(student.getId()));
		editEnrollmentDisplayStudentFirstName.setText(student.getFirstName());
		editEnrollmentDisplayStudentLastName.setText(student.getLastName());
		editEnrollmentDisplayStudentAddress.setText(student.getAddress());
		editEnrollmentDisplayStudentCity.setText(student.getCity());
		editEnrollmentDisplayStudentState.setText(student.getState());
		
		editEnrollmentYearChoiceBox.getItems().removeAll(createEnrollmentYearChoiceBox.getItems());
		editEnrollmentYearChoiceBox.getItems().addAll("2019", "2020", "2021", "2022");
		editEnrollmentYearChoiceBox.setValue(enrollment.getYear());
		editEnrollmentSemesterChoiceBox.getItems().removeAll(createEnrollmentSemesterChoiceBox.getItems());
		editEnrollmentSemesterChoiceBox.getItems().addAll("Spring", "Summer", "Fall", "Winter");
		editEnrollmentSemesterChoiceBox.setValue(enrollment.getSemester());
		setUpEditForEnrollmentCourse(enrollment.getCourseId());
	}
	
	private void displaySearchedEnrollment(int recordNumber) throws IOException{
		System.out.println("Record number: " + recordNumber);
		Enrollment enrollment = enrollmentFile.readSelectedEnrollment(recordNumber);
		System.out.println(enrollment.getSemester() + enrollment.getYear());
		makeAllInvisible();
		displayEnrollmentVBOX.setVisible(true);
		
		displayEnrollmentEID.setText(String.valueOf(enrollment.getEnrollmentId()));
		displayEnrollmentCID.setText(String.valueOf(enrollment.getCourseId()));
		displayEnrollmentSID.setText(String.valueOf(enrollment.getStudentId()));
		displayEnrollmentYear.setText(enrollment.getYear());
		displayEnrollmentSemester.setText(enrollment.getSemester());
		displayEnrollmentGrade.setText(enrollment.getGrade());
		
	}
	
	public void enrollmentSearchButtonListener() throws IOException{
		searchEnrollmentRequestedID = Integer.valueOf(enrollmentSearchID.getText());
		System.out.println("Requested ID: " + searchEnrollmentRequestedID);
		if(EIDCounter == 0)
			errorMessage("No enrollments have been created");
		else if(searchEnrollmentRequestedID < 1 || searchEnrollmentRequestedID > EIDCounter)
			errorMessage("Invalid search");
		else {
			enrollmentJustSearched = true;
			displaySearchedEnrollment(searchEnrollmentRequestedID);
		}
	}
	
	private void setUpEditForEnrollmentCourse(int courseID) throws IOException{
		editEnrollmentCourseChoiceBox.getItems().removeAll(editEnrollmentCourseChoiceBox.getItems());
		
		long numberOfCourses = courseFile.getNumberOfRecords();
		
		for(long i = 1; i <= numberOfCourses; i++) {
			int id = courseFile.readSelectedCourse(i).getId();
			String num = courseFile.readSelectedCourse(i).getNum();
			String option = String.valueOf(id) + "   " + num;
			
			editEnrollmentCourseChoiceBox.getItems().add(option);
		}
		
		String num = courseFile.readSelectedCourse(courseID).getNum();
		String option = String.valueOf(courseID) + "   " + num;
		editEnrollmentCourseChoiceBox.setValue(option);
	}
	////////
	// report
	public void reportSearchButtonListener() throws IOException{
		makeAllInvisible();
		displayReportVBOX.setVisible(true);
		listOfStudentInfo.getItems().removeAll(listOfStudentInfo.getItems());
		if(findEnrollment(extractID(searchReportCourseChoiceBox.getValue()), searchReportYearChoiceBox.getValue())) {
			listOfReportEnrollmentIds = findEnrollmentIDs(extractID(searchReportCourseChoiceBox.getValue()), searchReportYearChoiceBox.getValue());
			if(listOfReportEnrollmentIds.isEmpty()) {
				errorMessage("Invalid Search");
				return;
			}
			for(int EID : listOfReportEnrollmentIds) {
				Enrollment enrollment = enrollmentFile.readSelectedEnrollment(EID);
				Student student = studentFile.readSelectedStudent(enrollment.getStudentId());
				String str;
				str = "Student ID: " + student.getId() + ", Student Name: " + student.getFirstName().trim() + ", Grade: " + enrollment.getGrade();
				listOfStudentInfo.getItems().add(str);
			}
			
		}
		else {
			errorMessage("There are no enrollments with those details.");
		}
	}
	
	private int extractID(String string) {
		String str = "";
		for(int i = 0; i < string.length(); i++) {
			if(string.charAt(i) == ' ') break;
			else {
				str += string.charAt(i);
			}
		}
		return Integer.valueOf(str);
	}
	
	private void makeAllInvisible() {
		MenuLabel.setVisible(false);
		addStudentVBOX.setVisible(false);
		editStudentVBOX.setVisible(false);
		postAddStudentVBOX.setVisible(false);
		displayStudentVBOX.setVisible(false);
		searchStudentVBOX.setVisible(false);
		addCourseVBOX.setVisible(false);
		postAddCourseVBOX.setVisible(false);
		editCourseVBOX.setVisible(false);
		searchCourseVBOX.setVisible(false);
		displayCourseVBOX.setVisible(false);
		searchStudentCreateEnrollmentVBOX.setVisible(false);
		createEnrollmentHBOX.setVisible(false);
		postAddEnrollmentVBOX.setVisible(false);
		editEnrollmentHBOX.setVisible(false);
		searchEnrollmentGradeManagementVBOX.setVisible(false);
		displayEnrollmentVBOX.setVisible(false);
		gradeManagementEditVBOX.setVisible(false);
		searchEnrollmentVBOX.setVisible(false);
		displayReportVBOX.setVisible(false);
		searchReportVBOX.setVisible(false);
	}
	
	static long desiredRecordNumber;
	static Scanner cin = new Scanner(System.in);
	
	static Student student;
	static StudentFile studentFile;
	
	static Course course;
	static CourseFile courseFile;
	
	static Enrollment enrollment;
	static EnrollmentFile enrollmentFile;

	public static void main(String[] args) throws IOException{
	}

}
	
class Student{
	int id = 0; // 4 bytes
	String firstName = "", lastName = "", address = "", city = "", state = "";
	// 20 chars each -> 40 bytes each, 4 strings = 40 * 5 = 200
	// 204 bytes total 
	
	public Student() {}
	
	public Student(int id, String firstName, String lastName, String address, String city, String state) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
	}
	
	public void setId(int id) {this.id = id;}
	public void setFirstName(String firstName) {this.firstName = firstName;}
	public void setLastName(String lastName) {this.lastName = lastName;}
	public void setAddress(String address) {this.address = address;}
	public void setCity(String city) {this.city = city;}
	public void setState(String state) {this.state = state;}
	
	public int getId() {return this.id;}
	public String getFirstName() {return this.firstName;}
	public String getLastName() {return this.lastName;}
	public String getAddress() {return this.address;}
	public String getCity() {return this.city;}
	public String getState() {return this.state;}
	
	public String toString() {
		String str = "SID: " + this.id + "\nFirst Name: " + this.firstName + "\nLast Name: " + this.lastName +"\nAddress: " + this.address
				+ "\nCity: " + this.city + "\nState: " + this.state;
		
		return str;
	}
}

/*
 * int = 4 bytes
 * char = 2 bytes
 * long = 8 bytes
 * float = 4 bytes
 * double = 8 bytes
 * short  = 2 bytes
 */

class Course{
	int id = 0; // 4 bytes
	String num = "", name = "", instruct = "", department = "";
	// 160 bytes, 20 chars each
	// 164 bytes
	public Course() {}
	public Course(int id, String num, String name, String instruct, String department) {
		this.num = num;
		this.id = id;
		this.name = name;
		this.instruct = instruct;
		this.department = department;
	}
	
	public void setNum(String num) {this.num = num;}
	public void setId(int id) {this.id = id;}
	public void setName(String name) {this.name = name;}
	public void setInstruct(String instruct) {this.instruct = instruct;}
	public void setDepartment(String department) {this.department = department;}
	
	public String getNum() {return this.num;}
	public int getId() {return this.id;}
	public String getName() {return this.name;}
	public String getInstruct() {return this.instruct;}
	public String getDepartment() {return this.department;}
	
	public String toString() {
		String str = "CID: " + id + "\nCName: " + name + "\nInstruct: " + instruct + "\nDepartment: " + department +
				"\nCNUM: " + num;
		
		return str;
	}
}

/*
 * int = 4 bytes
 * char = 2 bytes
 * long = 8 bytes
 * float = 4 bytes
 * double = 8 bytes
 * short  = 2 bytes
 */

class Enrollment{
	int enrollmentId = 0; // 4
	int studentId = 0; // 4
	int courseId = 0; // 4
	String year = ""; // 40
	String semester = ""; // 20 * 2 = 40
	String grade = ""; // 20 * 2 = 40
	// 132
	public Enrollment() {}
	
	public Enrollment(int enrollmentId, int courseId, int studentId, String year, String semester, String grade) {
		this.enrollmentId = enrollmentId;
		this.studentId = studentId;
		this.courseId = courseId;
		this.year = year;
		this.semester = semester;
		this.grade = grade;
	}
	
	public Enrollment(int enrollmentId, int courseId, int studentId, String year, String semester) {
		this.enrollmentId = enrollmentId;
		this.studentId = studentId;
		this.courseId = courseId;
		this.year = year;
		this.semester = semester;
		this.grade = "NA";
	}
	
	public void setStudentId(int studentId) {this.studentId = studentId;}
	public void setCourseId(int courseId) {this.courseId = courseId;}
	public void setYear(String year) {this.year = year;}
	public void setSemester(String semester) {this.semester = semester;}
	public void setGrade(String grade) {this.grade = grade;}
	
	public int getEnrollmentId() {return this.enrollmentId;}
	public int getStudentId() {return this.studentId;}
	public int getCourseId() {return this.courseId;}
	public String getYear() {return this.year;}
	public String getSemester() {return this.semester;}
	public String getGrade() {return this.grade;}
	
	public String toString() {
		String str = "SID: " + studentId + " \nCID: " + courseId + "\nYear: " + year + "\nSemester: " + semester + "\nGrade: " + grade;
		
		return str;
	}
}

/*
 * int = 4 bytes
 * char = 2 bytes
 * long = 8 bytes
 * float = 4 bytes
 * double = 8 bytes
 * short  = 2 bytes
 */

class File{
	protected final long startOfFile = 0;
	private final int record_size = 0;
	protected RandomAccessFile file;
	
	
	protected File() {}
	
	protected File(String fileName) throws FileNotFoundException{
		file = new RandomAccessFile(fileName, "rw");
	}
	
	protected long getByteNum(long recordNum) {return recordNum;}
	
	
	public void moveFilePointer(long recordNum) throws IOException{
		file.seek(getByteNum(recordNum));
	}

	public void close() throws IOException{
		file.close();
	}
	
	protected void WriteString(String string) throws IOException{
		if(string.length() > 20) {
			for(int i = 0; i < 20; i++)
					file.writeChar(string.charAt(i));
		}
		else {
			file.writeChars(string);
			for(int i = 0; i < 20 - string.length(); i++)
				file.writeChar(' ');
		}
	}
	
	protected void WriteInt(int integer) throws IOException{
		file.writeInt(integer);
	}
	
	protected String ReadString() throws IOException{
		char[] charArray = new char[20];
		
		for(int i = 0; i < 20; i++)
			charArray[i] = file.readChar();
		
		String string = new String(charArray);
		
		string.trim();
		
		return string;
	}
	
	public long fileByteSize() throws IOException{
		return file.length();
	}
	
	public void reopen(String fileName) throws FileNotFoundException{
		this.file = new RandomAccessFile(fileName, "rw");
	}
	
	public void truncate() throws IOException{
		this.file.setLength(startOfFile);
	}
}

/*
 * int = 4 bytes
 * char = 2 bytes
 * long = 8 bytes
 * float = 4 bytes
 * double = 8 bytes
 * short  = 2 bytes
 */

class StudentFile extends File{
	// student properties: id, name, address, city, state
	private final String fileName = "StudentFile.dat";
	public final int record_size = 204;
	public StudentFile() {}
	public StudentFile(String fileName) throws FileNotFoundException{
		super(fileName);
	}
	
	public void moveFilePointerToEnd() throws IOException{
		file.seek(getByteNum(MenuBarController.SIDCounter -1 ));
	}
	
	public void writeStudentInfoSelected(Student student) throws IOException{
		int id = student.getId();
		String firstName = student.getFirstName();
		String lastName = student.getLastName();
		String address = student.getAddress();
		String city = student.getCity();
		String state = student.getState();
		
		WriteInt(id);
		WriteString(firstName);
		WriteString(lastName);
		WriteString(address);
		WriteString(city);
		WriteString(state);
		
		this.reset();
	}
	
	
	public void writeStudentInfo(Student student) throws IOException{
		int id = student.getId();
		String firstName = student.getFirstName();
		String lastName = student.getLastName();
		String address = student.getAddress();
		String city = student.getCity();
		String state = student.getState();
		
		WriteInt(id);
		WriteString(firstName);
		WriteString(lastName);
		WriteString(address);
		WriteString(city);
		WriteString(state);
		
		this.reset();
	}
	
	public void readAllStudentInfo() throws IOException{
		this.moveFilePointer(startOfFile);
		for(int i = 0; i < this.getNumberOfRecords(); i++) {
			System.out.println(""); // new line
			System.out.println("Record #" + (i + 1));
			System.out.println(this.readStudentInfo());
		}
			
		System.out.println("");
	}
	
	public Student readStudentInfo() throws IOException{
		int id = file.readInt();
		String firstName = ReadString();
		String lastName = ReadString();
		String address = ReadString();
		String city = ReadString();
		String state = ReadString();
		
		return new Student(id, firstName, lastName, address, city, state);
	}
	
	public Student readSelectedStudent(long recordNumber) throws IOException{
		recordNumber--; // for it to make sense, record 1 for the user is record 0 for us
		// thereofore we ask the use for a record number (1-oo);
		// if they put 1, they really mean record 0
		this.moveFilePointer(recordNumber);
		return this.readStudentInfo();
	}
	
	public void writeSelectedStudent(Student student, long recordNumber) throws IOException{
		recordNumber--;
		this.moveFilePointer(recordNumber);
		this.writeStudentInfo(student);
	}
	
	protected long getByteNum(long recordNum) {
		return record_size * recordNum;
	}
	
	public long getNumberOfRecords() throws IOException{
		return file.length() / record_size;
	}
	
	
	
	public void reset() throws FileNotFoundException, IOException{
		this.file.close();
		this.reopen(fileName);
	}
}

class CourseFile extends File{
	private final String fileName = "CourseFile.dat";
	public final int record_size = 164;
	public CourseFile() {}
	public CourseFile(String fileName) throws FileNotFoundException{
		super(fileName);
	}
	
	public void writeCourseInfoSelected(Course course) throws IOException{
		String num = course.getNum();
		int id = course.getId();
		String name = course.getName();
		String instruct = course.getInstruct();
		String department = course.getDepartment();
		
		WriteInt(id);
		WriteString(num);
		WriteString(name);
		WriteString(instruct);
		WriteString(department);
		
		reset();
	}
	
	public void writeCourseInfo(Course course) throws IOException{
		String num = course.getNum();
		int id = course.getId();
		String name = course.getName();
		String instruct = course.getInstruct();
		String department = course.getDepartment();
		
		WriteInt(id);
		WriteString(num);
		WriteString(name);
		WriteString(instruct);
		WriteString(department);
		
		reset();
	}
	
	public void moveFilePointerToEnd() throws IOException{
		file.seek(getByteNum(MenuBarController.CIDCounter - 1));
	}
	
	public Course readCourseInfo() throws IOException{
		int id = file.readInt();
		String num = ReadString();
		String name = ReadString();
		String instruct = ReadString();
		String department = ReadString();
		
		return new Course(id, num, name, instruct, department);
	}
	
	public Course readSelectedCourse(long recordNum) throws IOException{
		recordNum--;
		this.moveFilePointer(recordNum);
		return this.readCourseInfo();
	}
	
	public void readAllCourseInfo() throws IOException{
		this.moveFilePointer(startOfFile);
		for(int i = 0; i < this.getNumberOfRecords(); i++) {
			System.out.println(""); // newLine
			System.out.println("Record #" + (i + 1));
			System.out.println(this.readCourseInfo());
		}
		System.out.println("");
	}
	
	public void writeSelectedCourseInfo(Course course, long recordNumber) throws IOException{
		recordNumber--;
		this.moveFilePointer(recordNumber);
		this.writeCourseInfoSelected(course);
	}
	
	protected long getByteNum(long recordNum) {
		return record_size * recordNum;
	}
	
	public long getNumberOfRecords() throws IOException{
		return file.length() / record_size;
	}
	
	public void reset() throws FileNotFoundException, IOException{
		this.file.close();
		this.reopen(fileName);
	}
	
}

class EnrollmentFile extends File{
	private final String fileName = "EnrollmentFile.dat";
	private final int record_size = 132;
	
	public EnrollmentFile() {}
	public EnrollmentFile(String fileName) throws FileNotFoundException {
		super(fileName);
	}
	
	public void writeEnrollmentInfo(Enrollment enrollment) throws IOException{
		int enrollmentId = enrollment.getEnrollmentId();
		int courseId = enrollment.getCourseId();
		int studentId = enrollment.getStudentId();
		
		String year = enrollment.getYear();
		String semester = enrollment.getSemester();
		String grade = enrollment.getGrade();
		
		WriteInt(enrollmentId);
		WriteInt(courseId);
		WriteInt(studentId);
		WriteString(year);
		WriteString(semester);
		WriteString(grade);
		
		reset();
	}
	
	public Enrollment readEnrollmentInfo() throws IOException{
		int enrollmentId = this.file.readInt();
		int courseId = this.file.readInt();
		int studentId = this.file.readInt();
		
		
		String year = ReadString();
		String semester = ReadString();
		String grade = ReadString();
		
		return new Enrollment(enrollmentId, courseId, studentId,year,semester,grade);
	}
	
	public void writeSelectedEnrollmentInfo(Enrollment enrollment, long recordNumber) throws IOException{
		recordNumber--;
		this.moveFilePointer(recordNumber);
		this.writeEnrollmentInfo(enrollment);
	}
	
	public Enrollment readSelectedEnrollment(long recordNumber) throws IOException{
		recordNumber--;
		this.moveFilePointer(recordNumber);
		return this.readEnrollmentInfo();
	}
	
	public void readAllEnrollment() throws IOException{
		this.moveFilePointer(startOfFile);
		for(int i = 0; i < this.getNumberOfRecords(); i++) {
			System.out.println(""); 
			System.out.println("Record #" + (i + 1));
			System.out.println(this.readEnrollmentInfo());
		}
	}
	protected long getByteNum(long recordNum) {
		return record_size * recordNum;
	}
	
	public long getNumberOfRecords() throws IOException{
		return file.length() / record_size;
	}
	public void reset() throws FileNotFoundException, IOException{
		this.file.close();
		this.reopen(fileName);
	}
}
