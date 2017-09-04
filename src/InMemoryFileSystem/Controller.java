package InMemoryFileSystem;

import InMemoryFileSystem.Entities.Abstract.IDirectoryNode;

import java.util.Collection;
import java.util.HashMap;

public class Controller {

    private Service service;
    private Context context;
    Controller(Context context){
        this.service = new Service(context);
        this.context = context;
    }
    Controller(Service service, Context context){
        this.service = service;
        this.context = context;
    }

    //MakeFile
    public void mkfile(HashMap<String, String> args){
         this.service.MakeFile(args.get("name"));
    }

    public void mkdir(HashMap<String, String> args){
        this.service.MakeDirectory(args.get("name"));
    }

    public void list(){
        Collection<IDirectoryNode> nodes = this.service.GetNodes();

        for(IDirectoryNode node:nodes){
            System.out.println(node.getInitials() + " | " + node.getName());
        }
    }


}
