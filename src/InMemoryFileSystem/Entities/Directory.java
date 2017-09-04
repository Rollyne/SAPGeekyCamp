package InMemoryFileSystem.Entities;

import InMemoryFileSystem.Entities.Abstract.DirectoryNode;
import InMemoryFileSystem.Entities.Abstract.IDirectoryNode;

import java.util.Collection;
import java.util.TreeSet;

public class Directory extends DirectoryNode {

    private Collection<IDirectoryNode> nodes;

    public Directory(){this.nodes = new TreeSet<>();}
    public Directory(Collection<IDirectoryNode> nodes){
        this.nodes = nodes;
    }
    public Directory(Collection<IDirectoryNode> nodes, String name){this.nodes = nodes; setName(name);}

    public Collection<IDirectoryNode> getNodes(){
        return this.nodes;
    }

    public void addNode(IDirectoryNode node){
        this.nodes.add(node);
    }
}
