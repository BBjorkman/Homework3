package core.test;

import core.api.IInstructor;
import core.api.impl.Instructor;
import core.api.IAdmin;
import core.api.impl.Admin;
import core.api.IStudent;
import core.api.impl.Student;
import org.junit.Before;
import org.junit.Test;
import java.lang.String;

import static org.junit.Assert.*;

public class TestInstructor{
	private IAdmin admin;
	private IInstructor ins;
	private IStudent stud;
	private String sname;
	private String iname;
	private String cname;
	private String hname;
	private int year;
	@Before
	public void Init() {
		this.admin=new Admin();
		this.ins=new Instructor();
		this.stud=new Student();
		sname="W";
		iname="X";
		cname="Y";
		hname="Z";
		year=2020;
	}
	@Test
	public void normalAdd(){
		correctStart();
		assertTrue(this.ins.homeworkExists(cname, year, hname));
	}
	@Test
	public void rightInstructorWrongClass() {
		correctStart();
		this.admin.createClass("A", 2020, "B", 20);
		this.ins.addHomework("B", "Y", year, "C");
		assertFalse(this.ins.homeworkExists(cname, year, "C"));
	}
	@Test
	public void impostorYearAdd(){
		this.admin.createClass(cname, year+1, iname, 20);
		this.ins.addHomework(iname, cname, year, hname);
		assertFalse(this.ins.homeworkExists(cname, year, hname));
	}
	@Test
	public void imposterTeacherAdd(){
		this.admin.createClass(cname, year, iname+" FAKE", 20);
		this.ins.addHomework(iname, cname, year, hname);
		assertFalse(this.ins.homeworkExists(cname, year, hname));
	}
	@Test
	public void switchYear() {
		this.admin.createClass(cname, year, iname,20);
		this.admin.createClass(cname, year+1, iname, 20);
		this.ins.addHomework(iname, cname, year+1, hname);
		assertFalse(this.ins.homeworkExists(cname, year, hname));
	}
	@Test
	public void badClassAdd(){
		this.admin.createClass(cname, year, iname, -1);
		this.ins.addHomework(iname, cname, year, hname);
		assertFalse(this.ins.homeworkExists(cname, year, hname));
	}
	@Test
	public void noClassAdd(){
		this.ins.addHomework(iname, cname, year, hname);
		assertFalse(this.ins.homeworkExists(cname, year, hname));
	}
	@Test
	public void null1() {
		this.admin.createClass(cname, year, iname, 20);
		this.ins.addHomework(null, cname, year, hname);
		assertFalse(this.ins.homeworkExists(cname, year, hname));
	}
	@Test
	public void blank1() {
		this.admin.createClass(cname, year, iname, 20);
		this.ins.addHomework("", cname, year, hname);
		assertFalse(this.ins.homeworkExists(cname, year, hname));
	}
	@Test
	public void blank2() {
		this.admin.createClass(cname, year, iname, 20);
		this.ins.addHomework(iname, "", year, hname);
		assertFalse(this.ins.homeworkExists(cname, year, hname));
	}
	@Test
	public void blank3() {
		this.admin.createClass(cname, year, iname, 20);
		this.ins.addHomework(iname, cname, year, "");
		assertFalse(this.ins.homeworkExists(cname, year, hname));
	}
	@Test
	public void space1() {
		this.admin.createClass(cname, year, iname, 20);
		this.ins.addHomework(" ", cname, year, hname);
		assertFalse(this.ins.homeworkExists(cname, year, hname));
	}
	@Test
	public void space2() {
		this.admin.createClass(cname, year, iname, 20);
		this.ins.addHomework(iname, " ", year, hname);
		assertFalse(this.ins.homeworkExists(cname, year, hname));
	}
	@Test
	public void space3() {
		this.admin.createClass(cname, year, iname, 20);
		this.ins.addHomework(iname, cname, year, " ");
		assertFalse(this.ins.homeworkExists(cname, year, hname));
	}
	@Test
	public void oopsNoClass() {
		correctStart();
		assertTrue(this.admin.classExists(cname, year));
	}
	@Test
	public void oopsWrongInstructor() {
		correctStart();
		assertTrue(this.admin.getClassInstructor(cname, year)==iname);
	}
	@Test
	public void oopsWrongCap() {
		correctStart();
		assertTrue(this.admin.getClassCapacity(cname, year)==20);
	}
	@Test
	public void oopsNoHomework() {
		correctStart();
		this.ins.assignGrade(iname, cname, year, hname+" HEY", sname, -1);
		assertTrue(this.ins.getGrade(cname, year, hname, sname)==null);
	}
	@Test
	public void assignGrade100() {
		correctStart();
		this.stud.submitHomework(sname, hname, "Hello", cname, year);
		this.ins.assignGrade(iname, cname, year, hname, sname, 100);
		assertTrue(this.ins.getGrade(cname, year, hname, sname)==100);
	}
	@Test
	public void assignGrade0() {
		correctStart();
		this.stud.submitHomework(sname, hname, "Hello", cname, year);
		this.ins.assignGrade(iname, cname, year, hname, sname, 0);
		assertTrue(this.ins.getGrade(cname, year, hname, sname)==0);
	}
	@Test
	public void assignGradeBelowZero() {
		correctStart();
		this.stud.submitHomework(sname, hname, "Hello", cname, year);
		this.ins.assignGrade(iname, cname, year, hname, sname, -1);
		assertTrue(this.ins.getGrade(cname, year, hname, sname)==null);
	}
	@Test
	public void assignGrade101() {
		correctStart();
		this.stud.submitHomework(sname, hname, "Hello", cname, year);
		this.ins.assignGrade(iname, cname, year, hname, sname, 101);
		assertTrue(this.ins.getGrade(cname, year, hname, sname)==101);
	}
	@Test
	public void wrongYear() {
		correctStart();
		this.stud.submitHomework(sname, hname, "Hello", cname, year);
		this.ins.assignGrade(iname, cname, year+1, hname, sname, -1);
		assertTrue(this.ins.getGrade(cname, year, hname, sname)==null);
	}
	@Test
	public void wrongInstructorAssign() {
		correctStart();
		this.stud.submitHomework(sname, hname, "Hello", cname, year);
		this.ins.assignGrade(iname, cname, year, hname+" HEY", sname, -1);
		assertTrue(this.ins.getGrade(cname, year, hname, sname)==null);
	}
	@Test
	public void wrongStudent() {
		correctStart();
		this.stud.submitHomework(sname, hname, "Hello", cname, year);
		this.ins.assignGrade(iname, cname, year, hname, sname=" HEY", -1);
		assertTrue(this.ins.getGrade(cname, year, hname, sname)==null);
	}
	@Test
	public void reviseGrade() {
		correctStart();
		this.stud.submitHomework(sname, hname, "Hello", cname, year);
		this.ins.assignGrade(iname, cname, year, hname, sname, 90);
		this.ins.assignGrade(iname, cname, year, hname, sname, 80);
		assertTrue(this.ins.getGrade(cname, year, hname, sname)==80);
	}
	public void correctStart() {
		this.admin.createClass(cname, year, iname, 20);
		this.ins.addHomework(iname, cname, year, hname);
		this.stud.registerForClass(sname, cname, year);
	}
}