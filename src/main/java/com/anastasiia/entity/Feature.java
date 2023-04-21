package com.anastasiia.entity;
/**
 * <code>Feature</code> - class entity for table 'features'
 */
public class Feature extends Entity{
    private static final long serialVersionUID = -8649179320791110552L;
    private int id;
    private String name;
    private boolean isChecked = false;

    public Feature(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
