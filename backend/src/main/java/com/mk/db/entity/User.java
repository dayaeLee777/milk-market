package com.mk.db.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user")
public class User extends BaseEntity implements UserDetails {

	@Column
	private String password;
	
	@Column(unique = true)
	private String nickname;
	
	@Column(unique = true)
	private String email;

	@Column(columnDefinition = "boolean default false")
	private boolean delYn;

	private String sigungu;

	private String bcode;
	
	private String bname;
	
	@Column(name = "profile_image")
	private String profileImage;
	
	@Column(name = "profile_description")
	private String profileDescription;

	@Column(unique = true)
	private String walletAddress;

	@Column
	private String walletPrivateKey;


	@ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

	public User(String password, String nickname, String email, boolean delYn, String sigungu, String bcode, String bname, String profileImage, String profileDescription, String walletAddress, String walletPrivateKey, List<String> roles) {
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.delYn = delYn;
		this.sigungu = sigungu;
		this.bcode = bcode;
		this.bname = bname;
		this.profileImage = profileImage;
		this.profileDescription = profileDescription;
		this.walletAddress = walletAddress;
		this.walletPrivateKey = walletPrivateKey;
		this.roles = roles;
	}


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


	
}
