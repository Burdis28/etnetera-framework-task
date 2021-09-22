package com.etnetera.hr.data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 * 
 * @author Etnetera
 *
 */
@Entity
public class JavaScriptFramework {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;

	@Column(nullable = false)
	private ZonedDateTime deprecationDate;

	@OneToMany(mappedBy = "javaScriptFramework", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<FrameworkVersion> versions = new ArrayList<>();

	@Column(nullable = false)
	private Integer hypeLevel;

	public JavaScriptFramework() {
	}

	public JavaScriptFramework(String name) {
		this.name = name;
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

	public ZonedDateTime getDeprecationDate() {
		return deprecationDate;
	}

	public void setDeprecationDate(ZonedDateTime deprecationDate) {
		this.deprecationDate = deprecationDate;
	}

	public List<FrameworkVersion> getVersion() {
		return versions;
	}

	public void addVersion(FrameworkVersion version) {
		this.versions.add(version);
		version.setJavaScriptFramework(this);
	}

	public Integer getHypeLevel() {
		return hypeLevel;
	}

	public void setHypeLevel(Integer hypeLevel) {
		this.hypeLevel = hypeLevel;
	}

	@Override
	public String toString() {
		return "JavaScriptFramework [id=" + id + ", name=" + name + ", deprecation date=" + deprecationDate + "]";
	}

}
