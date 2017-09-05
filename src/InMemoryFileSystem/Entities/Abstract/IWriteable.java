package InMemoryFileSystem.Entities.Abstract;

public interface IWriteable {
    void write(String text);
    void writeLine(String text);
}
