package UniSystem.Repositories;

import UniSystem.Entities.Identificatable;
import UniSystem.UniSystemContext;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class Repository<TEntity extends Identificatable> implements IRepository<TEntity> {

    private UniSystemContext context;
    private final Supplier<? extends TEntity> entityConstructor;
    private String pluralFormOfEntity;
    public Repository(UniSystemContext context, Supplier<? extends TEntity> entityConstructor, String pluralFormOfEntity) {
        this.entityConstructor = Objects.requireNonNull(entityConstructor);
        this.context = context;
        this.pluralFormOfEntity = pluralFormOfEntity;
    }

    private List<TEntity> parseObjects(ResultSet rs) throws SQLException, IllegalAccessException {

        List<TEntity> items = new ArrayList<>();
        TEntity item = entityConstructor.get();
        final Field[] declaredFields = item.getClass().getDeclaredFields();

        while(rs.next()){
            for(Field field:declaredFields){
                field.setAccessible(true);
                field.set(item, rs.getObject(field.getName()));
            }
            items.add(item);
            item = entityConstructor.get();
        }

        return items;
    }

    private StringBuilder generateInsertValuesQuery(TEntity item, StringBuilder sql) throws IllegalAccessException {

        Field[] fields = item.getClass().getDeclaredFields();
        sql.append(" (");
        int lastFieldId = fields.length-1;
        for(int i = 1; i < lastFieldId; i++){
            fields[i].setAccessible(true);
            sql.append(fields[i].getName()).append(", ");
        }

        fields[lastFieldId].setAccessible(true);
        sql.append(fields[lastFieldId].getName());

        sql.append(") VALUES('");
        for(int i=1; i < lastFieldId; i++){
            sql.append(fields[i].get(item)).append("', '");
        }
        sql.append(fields[lastFieldId].get(item)).append("')");

        return sql;
    }

    public TEntity getById(int id) throws SQLException, IllegalAccessException {
        PreparedStatement cmd = context.getConnection().prepareStatement(
                String.format("SELECT * FROM %s WHERE students.id = ?", this.pluralFormOfEntity));
        cmd.setInt(1, id);
        ResultSet rs = cmd.executeQuery();

        return parseObjects(rs).get(0);
    }

    public List<TEntity> getAll() throws SQLException, IllegalAccessException {
        PreparedStatement cmd = context.getConnection().prepareStatement(
                String.format("SELECT * FROM %s", this.pluralFormOfEntity));
        ResultSet rs = cmd.executeQuery();

        return parseObjects(rs);
    }

    public void add(TEntity item) throws SQLException, IllegalAccessException {

        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO ").append(this.pluralFormOfEntity);

        sql = generateInsertValuesQuery(item, sql);

        PreparedStatement cmd = context.getConnection().prepareStatement(sql.toString());
        cmd.execute();
    }

    public void update(TEntity item) throws IllegalAccessException, SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(this.pluralFormOfEntity).append(" SET ");

        Field[] fields = item.getClass().getDeclaredFields();
        int lastFieldId = fields.length-1;

        for(int i = 1; i < lastFieldId;i++){
            fields[i].setAccessible(true);
            sql.append(fields[i].getName()).append(" = '").append(fields[i].get(item)).append("', ");
        }
        fields[lastFieldId].setAccessible(true);
        sql.append(fields[lastFieldId].getName()).append(" = '").append(fields[lastFieldId].get(item)).append("'");

        sql.append(" WHERE ").append(" id = ").append(item.getId());

        PreparedStatement cmd = context.getConnection().prepareStatement(sql.toString());
        cmd.execute();
    }

    public void delete(int id) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(this.pluralFormOfEntity).append(" WHERE id = ").append(id);

        PreparedStatement cmd = context.getConnection().prepareStatement(sql.toString());
        cmd.execute();
    }


}
