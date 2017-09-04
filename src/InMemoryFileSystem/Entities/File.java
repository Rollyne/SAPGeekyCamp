package InMemoryFileSystem.Entities;

import InMemoryFileSystem.Entities.Abstract.DirectoryNode;

public class File extends DirectoryNode {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
