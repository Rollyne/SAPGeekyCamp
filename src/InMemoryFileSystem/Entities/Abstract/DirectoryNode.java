package InMemoryFileSystem.Entities.Abstract;

public abstract class DirectoryNode implements IDirectoryNode, Comparable<DirectoryNode> {

    protected String name;

    public DirectoryNode(String name){
        setName(name);
    }
    public DirectoryNode(){}

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int compareTo(DirectoryNode other){
        return this.name.compareTo(other.getName());
    }

    public char getInitials(){
        return this.getClass().getName().charAt(0);
    }
}
