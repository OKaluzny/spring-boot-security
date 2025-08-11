package org.example.springbootsecurity.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

    // Під час серіалізації, середовище виконання
    // Java створює номер версії для класу (SerialVersionUID),
    // так що воно може десереалізувати його пізніше.
    // Якщо під час десеріалізації, SerialVersionUID не відповідний,
    // процес завершиться з винятком InvalidClassException в потоці main.
    //
    // serialVersionUID — універсальний ідентифікатор версії
    // для Serializable класу. Десеріалізація використовує це число,
    // щоб гарантувати, що завантажений клас точно відповідає
    // серіалізованому об’єкту.
    // Якщо відповідності не знайдено, створюється виняткова ситуація
    // InvalidClassException.
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // nullable = false - поле не може бути null
    @Column(nullable = false)
    private String name;
    // unique = true - поле має бути унікальним
    // у таблиці бази даних
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    // @ManyToMany з JPA та вказує, що зв’язок між сутностями
    // User та Role є «багато-до-багатьох».
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // @JoinTable з JPA, визначає деталі таблиці об’єднання,
    // яка використовується для реалізації зв’язку «багато-до-багатьох»
    // між сутностями User та Role.
    // Визначає назву таблиці об’єднання (users_roles) і імена стовпців
    // зовнішнього ключа в таблиці об’єднання (USER_ID і ROLE_ID).
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(Long id, String name, String email, String password, List<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
