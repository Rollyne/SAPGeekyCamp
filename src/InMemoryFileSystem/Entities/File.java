package InMemoryFileSystem.Entities;

import InMemoryFileSystem.Entities.Abstract.*;

import java.nio.channels.FileLockInterruptionException;

public class File extends DirectoryNode implements IReadable, IWriteable, IDisposable {

    private StringBuilder content;
    private FileOpenMode openMode = null;

    public File(){
        this.content = new StringBuilder();
    }

    public File(StringBuilder content){
        this.content = content;
    }


    @Override
    public String readToEnd() {
        return content.toString();
    }

    @Override
    public void write(String text) {
        content.append(text);
    }

    @Override
    public void writeLine(String text) {
        content.append(text).append('\n');
    }

    public void open(FileOpenMode mode) throws FileLockInterruptionException {
        if(this.openMode != null){
            throw new FileLockInterruptionException();
        }
        this.openMode = mode;
    }

    public boolean isOpened() {
        return this.openMode != null;
    }

    public FileOpenMode getOpenMode() {
        return openMode;
    }

    public void setOpenMode(FileOpenMode openMode) {
        this.openMode = openMode;
    }

    @Override
    public void dispose() {
        this.openMode = null;
    }
}
