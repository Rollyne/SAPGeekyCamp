package InMemoryFileSystem;


import InMemoryFileSystem.Entities.Abstract.IDirectoryNode;
import InMemoryFileSystem.Entities.Directory;
import InMemoryFileSystem.Entities.File;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

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

    public void ChangeDirectory(String dir){
        for(IDirectoryNode node : context.getCurrentDirectory().getNodes()){
            if(node instanceof Directory){
                if(Objects.equals(node.getName(), dir)){
                    context.setCurrentDirectory((Directory)node);
                }
            }
        }
    }
}
