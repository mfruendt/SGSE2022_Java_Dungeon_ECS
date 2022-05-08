class XTest
{
    public static String concat(String s1, String s2)
    {
        return s1 + null;
    }
}

public class XTest
{
    @Test(expected = NullPointerException.class)
    public void testConcatWithNullS1()
    {
        concat(null, "blah");
    }

    @Test(expected = NullPointerException.class)
    public void testConcatWithNullS2()
    {
        concat("blah", null);
    }

    @Test
    public void testValidConcat()
    {
        assertEquals("blahblah", concat("blah", "blah"));
    }
}