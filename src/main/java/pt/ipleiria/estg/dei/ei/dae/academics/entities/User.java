package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User implements Serializable {
    @Id
    protected String username;

    @Version
    private int version;

    @NotNull
    @Column(nullable = false)
    protected String password;

    @NotNull
    protected String name;

    @NotNull
    @Email
    protected String email;

    public User() {
    }

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = hashPassword(password);
        this.name = name;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public static String hashPassword(String password){
        char[] encoded = null;
        try {
            ByteBuffer passwdBuffer = Charset.defaultCharset().encode(CharBuffer.wrap(password));
            byte[] passwdBytes = passwdBuffer.array();
            MessageDigest mdEnc = MessageDigest.getInstance("SHA-256");
            mdEnc.update(passwdBytes, 0, password.toCharArray().length);
            encoded = new BigInteger(1, mdEnc.digest()).toString(16).toCharArray();
        } catch(NoSuchAlgorithmException e){
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
        }

        return new String(encoded);
    }
}
