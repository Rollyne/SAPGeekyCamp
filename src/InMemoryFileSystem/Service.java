package InMemoryFileSystem;


import InMemoryFileSystem.Entities.Abstract.IDirectoryNode;
import InMemoryFileSystem.Entities.Directory;
import InMemoryFileSystem.Entities.File;

import java.util.Collection;

public class Service {

    private Context context;

    Service(Context context){
        this.context = context;
    }

    //Make file
    public void MakeFile(String name) {

        File file = new File();
        file.setName(name);
        context.getCurrentDirectory().addNode(file);
    }

    public void MakeDirectory(String name){
        Directory dir = new Directory();
        dir.setName(name);
        context.getCurrentDirectory().addNode(dir);
    }

    public Collection<IDirectoryNode> GetNodes(){
        return context.getCurrentDirectory().getNodes();
    }
}
