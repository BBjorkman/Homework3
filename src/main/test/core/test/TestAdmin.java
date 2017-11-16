package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAdmin{
	private IAdmin admin;
	@Before
	public void Init() {
		this.admin=new Admin();
	}
	@Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }
    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    @Test
    public void testMakeClass3() {
        this.admin.createClass("", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    @Test
    public void testMakeClass4() {
        this.admin.createClass("Test", 2017, "", 15);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    @Test
    public void testMakeClass6() {
        this.admin.createClass("Test", 2017, null, 15);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    @Test
    public void testMakeClass7() {
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    @Test
    public void testMakeClass8() {
        this.admin.createClass("Test", 2017, "Instructor", -1);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    @Test
    public void testMakeClass9() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test", 2017, 0);
        assertFalse(this.admin.getClassCapacity("Test", 2017)==0);
    }
    @Test
    public void testMakeClass10() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test", 2017, -1);
        assertFalse(this.admin.getClassCapacity("Test", 2017)==-1);
    }
    @Test
    public void testMakeClass11() {
    		this.admin.createClass("A",2020,"B",20);
    		this.admin.createClass("A",2020,"C",21);
    		assertFalse(this.admin.getClassCapacity("A", 2020)==21);
    }
    @Test
    public void testMakeClass12() {
		this.admin.createClass("A",2020,"B",20);
		this.admin.createClass("C",2020,"B",21);
		assertFalse(this.admin.classExists("C", 2020)&&this.admin.classExists("A", 2020));
    }
}