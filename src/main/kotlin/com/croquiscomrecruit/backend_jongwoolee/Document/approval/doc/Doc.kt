package com.croquiscomrecruit.backend_jongwoolee.Document.approval.doc

import com.croquiscomrecruit.backend_jongwoolee.Document.approval.auth.User
import com.croquiscomrecruit.backend_jongwoolee.Document.approval.common.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*
import javax.persistence.*

enum class DocStatus {
        PENDING, APPROVED, DENIED
}

enum class ApprovalStatus {
        RESPOND, WAIT, APPROVED, DENIED, IGNORE
}

@Entity
data class Doc(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        val title: String,
        @Column(columnDefinition = "TEXT")
        val content: String,
        @ManyToOne
        val category: Category?,

        @Enumerated(EnumType.STRING)
        val status: DocStatus,

        @Temporal(TemporalType.TIMESTAMP)
        val regDate: Date,
        @ManyToOne
        val user: User,
)
interface DocRepository: JpaRepository<Doc, Int> {
        fun findByUserAndStatusIn(user: User, status: List<DocStatus>, pageable: Pageable): Page<Doc>
}

@Entity
data class DocApproval(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        @ManyToOne
        val doc: Doc,
        @ManyToOne
        val user: User,

        val seq: Int,

        @Enumerated(EnumType.STRING)
        val status: ApprovalStatus,
        val comment: String?,

        @Temporal(TemporalType.TIMESTAMP)
        val regDate: Date,

        @Temporal(TemporalType.TIMESTAMP)
        val modDate: Date,
)
interface DocApprovalRepository: JpaRepository<DocApproval, Int> {
        fun findAllByUserAndStatusIn(user: User, status: List<ApprovalStatus>, pageable: Pageable): Page<DocApproval>
        fun findByUserAndDoc(user: User, doc: Doc): DocApproval?
        fun findByDocAndSeq(doc: Doc, seq: Int): DocApproval?
        fun findAllByDocAndSeqGreaterThan(doc: Doc, seq: Int): List<DocApproval>

        @Query("SELECT a.doc FROM DocApproval a WHERE a.user = :user AND a.doc.status IN :status")
        fun findUserApprovals(user: User, status: List<DocStatus>): List<Doc>

        @Query(value =
                "SELECT * FROM doc d WHERE d.id in " +
                "(SELECT doc.id FROM doc WHERE user_id = ?1 AND status IN ('APPROVED', 'DENIED')) " +
                "or d.id in " +
                "(SELECT doc.id FROM doc INNER JOIN (SELECT doc_id FROM doc_approval WHERE user_id = ?1) da " +
                "ON doc.id = da.doc_id WHERE doc.status IN ('APPROVED', 'DENIED')) ORDER BY reg_date ",
                countQuery =
                "SELECT count(*) FROM doc d WHERE d.id in " +
                "(SELECT doc.id FROM doc WHERE user_id = ?1 AND status IN ('APPROVED', 'DENIED')) " +
                "or d.id in " +
                "(SELECT doc.id FROM doc INNER JOIN (SELECT doc_id FROM doc_approval WHERE user_id = ?1) da " +
                "ON doc.id = da.doc_id WHERE doc.status IN ('APPROVED', 'DENIED')) ",
                nativeQuery = true,
        )
        fun findDocCompleted(userId: String, pageable: Pageable): Page<Array<Object>>
}