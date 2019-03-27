package com.form.builder.configservice.module.base.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.Type;

@MappedSuperclass
public abstract class BaseEntity implements Serializable, Comparable<BaseEntity> {
	private static final long serialVersionUID = 1598270721745472357L;
	
	@Id
	@Column(unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	
	@Column(columnDefinition = "uuid", unique=true, nullable=false, updatable=false, length = 36)
	@Type(type="pg-uuid")
	private UUID guid;
	
	@Column(nullable=false)
	@Version
	protected int version;
	
	@Column(nullable=false)
	protected Timestamp lastUpdate;
	
	@Column(nullable=false)
	protected Timestamp created;
	
	@Column(nullable=false)
	protected Boolean deleted;
	
	public BaseEntity() {
		super();
		this.version = 0;
		this.created = new Timestamp(System.currentTimeMillis());
		this.lastUpdate = new Timestamp(System.currentTimeMillis());
		this.deleted = false;
		this.guid = UUID.randomUUID();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getGuid() {
		return guid;
	}

	public void setGuid(UUID guid) {
		this.guid = guid;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	@Override
	public int hashCode() {
		return guid.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj.getClass() != this.getClass()) return false;
		BaseEntity other = (BaseEntity) obj;
		if (this.guid.equals(other.guid)) return true;
		return false;
	}

	@Override
	public int compareTo(BaseEntity o) {
		//if other object, its guid or created time is null, we consider 'this' to be greater
		if (o == null) return Integer.MAX_VALUE;
		if (o.guid == null) return Integer.MAX_VALUE;
		if (o.created == null) return Integer.MAX_VALUE;
		
		if (this.guid == null) return Integer.MIN_VALUE;
		if (this.equals(o)) return 0;
		return (this.created.compareTo(o.created));
	}
}
