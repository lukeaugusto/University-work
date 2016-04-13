public class Driver {

	public static void main(String[] args) {
		Editor editor = new Editor();

		editor.insertInTheEnd("All work and no play makes Jack a dull boy,");
		editor.insertInTheEnd("All play and no work makes Jack a mere toy.");
		System.out.println(editor.showText());
		
		editor.insert(43,'\n');
		editor.insertInTheEnd('.');
		System.out.println(editor.showText());
		editor.delete(43, 87);

		editor.delete(10,11);
		editor.delete(42);
		editor.insertInTheEnd('.');
		System.out.println(editor.showText());

	}
}