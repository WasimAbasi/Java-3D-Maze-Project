package controller;

import view.MyView;

/**
 * Class Dir defines a dir command which displays all files and directories under
 * a given path.
 * @author Roaa, Wasim
 *
 */
public class Dir implements Command {
	
	MyView view;
	
	public Dir(MyView view) {
		this.view = view;
	}

	@Override
	public void doCommand(String[] commandParameters) {
		try {
			if(commandParameters[1] != null){
				String path = commandParameters[1];
				view.dir(path);
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		catch(NullPointerException e){
			e.printStackTrace();
		}
	}
}
