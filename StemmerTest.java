
import java.lang.reflect.Method;
import snowball.*;

class StemmerTest {
	public static void main(String[] args) throws Throwable {
		String[] test = {"computer", "computation", "compute", "evaluation", "evaluate",
				"evaluations", "tests", "testing", "testes"};

		Class stemClass = Class.forName("snowball.englishStemmer");
        SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();

        for(int i=0; i<test.length; i++) {
        	System.out.println(test[i]);
        	stemmer.setCurrent(test[i]);
        	stemmer.stem();
        	System.out.println(stemmer.getCurrent() +"\n");
        }
	}
}