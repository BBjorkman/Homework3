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

public class TestStudent{
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
		correctStart();
	}
	@Test
	public void normalRegister() {
		this.stud.registerForClass(sname, cname, year);
		assertTrue(this.stud.isRegisteredFor(sname, cname, year));
	}
	@Test
	public void yearDoesntMatter() {
		this.stud.registerForClass(sname, cname, year+1);
		assertFalse(this.stud.isRegisteredFor(sname, cname, year));
	}
	@Test
	public void classNonexistant() {
		this.stud.registerForClass(sname, cname+" Hi", year);
		assertFalse(this.stud.isRegisteredFor(sname, cname+" Hi", year));
	}
	@Test
	public void classFill() {
		this.admin.changeCapacity(cname, year, 2);
		this.stud.registerForClass("A",cname,year);
		this.stud.registerForClass("B",cname,year);
		assertTrue(this.stud.isRegisteredFor("B", cname, year));
	}
	@Test
	public void classFull() {
		this.admin.changeCapacity(cname, year, 2);
		this.stud.registerForClass("A",cname,year);
		this.stud.registerForClass("B",cname,year);
		this.stud.registerForClass("C", cname, year);
		assertFalse(this.stud.isRegisteredFor("C", cname, year));
	}
	@Test
	public void classTop() {
		this.admin.changeCapacity(cname, year, 2);
		this.stud.registerForClass("A",cname,year);
		this.stud.registerForClass("B",cname,year);
		this.stud.registerForClass("C", cname, year);
		assertTrue(this.stud.isRegisteredFor("B", cname, year));
	}
	@Test
	public void lookingAheadOfSchedule() {
		this.stud.registerForClass(sname, cname, year);
		this.stud.submitHomework(sname, hname, "Hello", cname, year);
		assertFalse(this.stud.hasSubmitted(sname, hname, cname, year));
	}
	@Test
	public void wrongYearStupid() {
		this.stud.registerForClass(sname, cname, year);
		this.ins.addHomework(iname, cname, year, hname);
		this.stud.submitHomework(sname, hname, "Hello", cname, year+1);
		assertFalse(this.stud.hasSubmitted(sname, hname, cname, year+1));
	}
	@Test
	public void wrongYearStupidStill() {
		this.stud.registerForClass(sname, cname, year);
		this.ins.addHomework(iname, cname, year, hname);
		this.stud.submitHomework(sname, hname, "Hello", cname, year-1);
		assertFalse(this.stud.hasSubmitted(sname, hname, cname, year-1));
	}
	@Test
	public void wrongClassStupid() {
		this.stud.registerForClass(sname, cname, year);
		this.ins.addHomework(iname, cname, year, hname);
		this.admin.createClass("newclass", year, iname, 20);
		this.stud.submitHomework(sname, hname, "Hello", "newclass", year);
		assertFalse(this.stud.hasSubmitted(sname, hname, "newclass", year));
	}
	@Test
	public void registeredLate() {
		this.ins.addHomework(iname, cname, year, hname);
		this.stud.registerForClass(sname, cname, year);
		this.stud.submitHomework(iname, hname, "Hi there!", cname, year);
		assertTrue(this.stud.hasSubmitted(sname, hname, cname, year));
	}
	@Test
	public void wishyWashy() {
		this.stud.registerForClass(sname, cname, year);
		this.stud.dropClass(sname, cname, year);
		this.stud.registerForClass(sname, cname, year);
		assertTrue(this.stud.isRegisteredFor(sname, cname, year));
	}
	@Test
	public void stillEnrolledInMind() {
		this.stud.registerForClass(sname, cname, year);
		this.stud.dropClass(sname, cname, year);
		this.stud.submitHomework(sname, hname, "Hello", cname, year);
		assertFalse(this.stud.hasSubmitted(sname, hname, cname, year));
	}
	@Test
	public void cantDropAnymoreStupid() {
		this.stud.registerForClass(sname, cname, year);
		this.admin.createClass(cname, year+1, iname, 20);
		this.stud.dropClass(sname, cname, year+1);
		assertTrue(this.stud.isRegisteredFor(sname, cname, year));
	}
	@Test
	public void cantKickStudents() {
		this.stud.registerForClass("A",cname,year);
		this.stud.registerForClass("B",cname,year);
		this.stud.registerForClass("C", cname, year);
		this.admin.changeCapacity(cname, year, 2);
		assertTrue(this.stud.isRegisteredFor("B", cname, year));
	}
	@Test
	public void cantKickStudents2() {
		this.stud.registerForClass("A",cname,year);
		this.stud.registerForClass("B",cname,year);
		this.stud.registerForClass("C", cname, year);
		this.admin.changeCapacity(cname, year, 2);
		assertTrue(this.admin.getClassCapacity(cname, year)==20);
	}
	public void correctStart() {
		this.admin.createClass(cname, year, iname, 20);
		this.ins.addHomework(iname, cname, year, hname);
	}
}