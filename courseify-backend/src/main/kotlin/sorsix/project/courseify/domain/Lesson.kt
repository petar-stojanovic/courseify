package sorsix.project.courseify.domain

import jakarta.annotation.Nullable
import jakarta.persistence.*

@Entity
@Table(name = "lesson")
data class Lesson(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val title: String,

    val description: String,

    @Column(name = "video_title")
    val videoTitle: String,

    @Column(name = "video_url")
    val videoUrl: String,

    @Column(name = "file_title")
    val fileTitle: String,

    @Column(name="file_url")
    val fileUrl: String,

    @ManyToOne
    @JoinColumn(name = "course_id")
    val course: Course,

    @OneToOne
    @Nullable
    @JoinColumn(name = "quiz_id")
    val quiz: Quiz?
) {
}
