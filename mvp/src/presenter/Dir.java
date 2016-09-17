package presenter;

import view.View;

/**
 * Class Dir defines a dir command which displays all files and directories under
 * a given path.
 * @author Roaa, Wasim
 *
 */
public class Dir implements Command {
	
	View view;
	
	public Dir(View view) {
		this.view = view;
	}

	@Override
	public void doCommand(String[] commandParameters) {
		try {
			if(commandParameters[1] != null){
				String path = commandParameters[1];
				view.dir(path);
			}
			else{
				view.error("Invalid Argument!");
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			view.error(e.getMessage());
		}
		catch(NullPointerException e){
			view.error(e.getMessage());
		}
	}
}
