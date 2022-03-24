package com.mk.db.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

	
	@Column(name = "profile_image")
	private String profileImage;
	
	@Column(name = "profile_description")
	private String profileDescription;

	@Column(unique = true)
	private String walletAddress;

	@ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

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

	@Override
	public String toString() {
		return "User{" +
				"id=" + getId() +
				", password='" + password + '\'' +
				", nickname='" + nickname + '\'' +
				", email='" + email + '\'' +
				", walletAddress='" + walletAddress + '\'' +
				", profileImage='" + profileImage + '\'' +
				", profileDescription='" + profileDescription + '\'' +
				", roles=" + roles +
				'}';
	}
}
