package com.sricode.crudadvancedjpa.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "instructor_detail")
@Data
public class InstructorDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer instructorId;
    @Column(name = "youtube_channel")
    private String youtubeChannel;
    @Column(name = "hobby")
    private String hobby;
    @OneToOne(mappedBy = "instructorDetails", cascade =
            {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Instructor instructor;
}
