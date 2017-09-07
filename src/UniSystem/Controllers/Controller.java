package UniSystem.Controllers;

import UniSystem.Entities.Identificatable;
import UniSystem.Services.ICrudService;
import UniSystem.Services.Tools.ExecutionInfo;
import UniSystem.Services.Tools.ExecutionResult;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public abstract class Controller<TEntity extends Identificatable> {

    protected ICrudService<TEntity> service;
    private Supplier<? extends TEntity> entityConstructor;

    Controller(ICrudService<TEntity> service){
        this.service = service;
        entityConstructor = setupEntityConstructor();
    }
    Controller() throws SQLException {
        setupService();
        entityConstructor = setupEntityConstructor();
    }

    protected abstract void setupService() throws SQLException;
    protected abstract Supplier<? extends TEntity> setupEntityConstructor();

    public void run() throws IllegalAccessException, SQLException {

        Scanner s = new Scanner(System.in);
        while(true){
            System.out.printf("\nMenu %s\n", this.service.getPluralFormOfEntity());
            System.out.println("1. Add");
            System.out.println("2. Update");
            System.out.println("3. List");
            System.out.println("4. Details");
            System.out.println("5. Delete");
            System.out.println("0. Exit");

            int input = s.nextInt();

            switch(input){
                case 1:
                    this.add();
                    break;
                case 2:
                    this.update();
                    break;
                case 3:
                    this.list();
                    break;
                case 4:
                    this.details();
                    break;
                case 5:
                    this.delete();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid command!");
                    break;
            }
        }


    }

    private void setFieldValue(TEntity item, String input, Field field) throws IllegalAccessException {
        if(field.getType() == int.class){
            field.set(item, Integer.parseInt(input));
        } else{
            field.set(item, input);
        }
    }

    private void printItem(Field[] fields, TEntity item) throws IllegalAccessException {
        for(Field field :fields){
            field.setAccessible(true);
            System.out.printf("%s: %s\n", field.getName(), field.get(item));
        }
    }

    private void add() throws IllegalAccessException {
        Scanner s = new Scanner(System.in);

        TEntity item = entityConstructor.get();
        Field[] fields = item.getClass().getDeclaredFields();

        for(int i = 1; i < fields.length; i++){
            fields[i].setAccessible(true);
            System.out.printf("%s: ", fields[i].getName());
            String input = s.nextLine();

            setFieldValue(item, input, fields[i]);
        }

        ExecutionInfo info = this.service.add(item);
        System.out.println(info.message);
    }

    private void update() throws IllegalAccessException {
        Scanner s = new Scanner(System.in);

        System.out.println("Id of the target: ");
        int id = Integer.parseInt(s.nextLine());

        ExecutionResult<TEntity> execution = this.service.getById(id);
        if(!execution.succeeded){
            System.out.println(execution.message);
            return;
        }

        TEntity item = execution.result;
        Field[] fields = item.getClass().getDeclaredFields();
        for(int i = 1; i < fields.length; i++){
            fields[i].setAccessible(true);
            System.out.printf("%s (%s): ", fields[i].getName(), fields[i].get(item));
            String input = s.nextLine();
            if(input.isEmpty()){
                fields[i].set(item, fields[i].get(item));
            }
            else{
                setFieldValue(item, input, fields[i]);
            }
        }

        ExecutionInfo info = this.service.update(item);
        System.out.println(info.message);
    }

    private void list() throws IllegalAccessException {

        ExecutionResult<List<TEntity>> execution = this.service.getAll();
        if(!execution.succeeded){
            System.out.println(execution.message);
            return;
        }
        if(execution.result.size() <= 0){
            System.out.println("No items to show.");
            return;
        }
        Field[] fields = execution.result.get(0).getClass().getDeclaredFields();

        for(TEntity item: execution.result){
            System.out.println("____________\n");
            printItem(fields, item);
        }
    }

    public int details() throws IllegalAccessException, SQLException {

        Scanner s = new Scanner(System.in);
        System.out.println("Id of the target: ");
        int id = Integer.parseInt(s.nextLine());

        ExecutionResult<TEntity> execution = this.service.getById(id);
        if(!execution.succeeded){
            System.out.println(execution.message);
            return id;
        }
        Field[] fields = execution.result.getClass().getDeclaredFields();
        printItem(fields, execution.result);

        return id;
    }

    private void delete() {
        Scanner s = new Scanner(System.in);
        System.out.println("Id of the target: ");
        int id = Integer.parseInt(s.nextLine());

        ExecutionInfo info = this.service.delete(id);
        System.out.println(info.message);
    }
}
