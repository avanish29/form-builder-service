package com.form.builder.configservice.module.usermanagement.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import com.form.builder.configservice.module.base.entity.BaseEntity;

@Entity
@Table(name = "app_user")
@NamedQueries({
    @NamedQuery(name = User.GET_USER_BY_USER_NAME, query="SELECT c FROM User c where c.userName = :userName")          
})
//@Audited 
public class User extends BaseEntity {
	private static final long serialVersionUID = 1280518336176118837L;
	public static final String GET_USER_BY_USER_NAME = "USER.FIND.BY.USERNAME";
	
	@Column(name = "first_name", nullable = false)
    @Length(min = 2, max = 128)
    private String firstName;
	
	@Column(name = "last_name", nullable = false)
    @Length(min = 2, max = 128)
    private String lastName;
	
    @Column(name = "user_name", nullable = false)
    @Length(max = 128)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "email_verified")
    protected Boolean emailVerified;

	@Column(name = "email_verified_time")
	private Timestamp emailVerifiedTime;
    
	@Column(name = "last_login_failure_time")
    public Timestamp lastLoginFailureTime;

	@Column(name = "last_password_changed_time")
	public Timestamp lastPasswordChangedTime;
	
	@Column(name = "password_reset_token")
	private String passwordResetToken;

	@Column(name = "password_reset_token_generated_time")
	private Timestamp passwordResetTokenGeneratedTime;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(name = "last_loggedin", nullable = true)
    private Timestamp lastLoggedIn;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "password_history", nullable = true)
    private String passWordHistory;

    @ManyToMany
    @JoinTable(name = "app_users_roles", 
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name="USER_ID_FK")), 
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), foreignKey = @ForeignKey(name="ROLE_ID_FK")) 
    private Collection<Role> roles = new ArrayList<>();

    @Column(name = "fail_count", nullable = false)
    private int failCount;

    @Column(name = "language", nullable = false)
    private String language;
    
    @Column(name = "tc_active", nullable =false)
    private boolean tcActive;

    public User() {
    	
    }
    
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Timestamp getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(Timestamp lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}

	public String getPassWordHistory() {
		return passWordHistory;
	}

	public void setPassWordHistory(String passWordHistory) {
		this.passWordHistory = passWordHistory;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean isTcActive() {
		return tcActive;
	}

	public void setTcActive(boolean tcActive) {
		this.tcActive = tcActive;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public Timestamp getEmailVerifiedTime() {
		return emailVerifiedTime;
	}

	public void setEmailVerifiedTime(Timestamp emailVerifiedTime) {
		this.emailVerifiedTime = emailVerifiedTime;
	}

	public Timestamp getLastLoginFailureTime() {
		return lastLoginFailureTime;
	}

	public void setLastLoginFailureTime(Timestamp lastLoginFailureTime) {
		this.lastLoginFailureTime = lastLoginFailureTime;
	}

	public Timestamp getLastPasswordChangedTime() {
		return lastPasswordChangedTime;
	}

	public void setLastPasswordChangedTime(Timestamp lastPasswordChangedTime) {
		this.lastPasswordChangedTime = lastPasswordChangedTime;
	}

	public String getPasswordResetToken() {
		return passwordResetToken;
	}

	public void setPasswordResetToken(String passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}

	public Timestamp getPasswordResetTokenGeneratedTime() {
		return passwordResetTokenGeneratedTime;
	}

	public void setPasswordResetTokenGeneratedTime(Timestamp passwordResetTokenGeneratedTime) {
		this.passwordResetTokenGeneratedTime = passwordResetTokenGeneratedTime;
	}
	
	@Transient
	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		if(this.roles == null) this.roles = new ArrayList<>();
		this.roles.add(role);
	}
}
