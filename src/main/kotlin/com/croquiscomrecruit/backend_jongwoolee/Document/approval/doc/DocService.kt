package com.croquiscomrecruit.backend_jongwoolee.Document.approval.doc

import com.croquiscomrecruit.backend_jongwoolee.Document.approval.auth.User
import com.croquiscomrecruit.backend_jongwoolee.Document.approval.common.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import java.util.*

@Service
class DocService @Autowired constructor(
        val docRepository: DocRepository,
        val docApprovalRepository: DocApprovalRepository
) {
    @Transactional
    fun create(param: ReqCreateDoc, userId: String): ResDoc {
        val newDoc = Doc(
                id = 0,
                title= param.title,
                content = param.content,
                category = if(param.category == -1) null
                           else Category(param.category),
                status =
                        if(param.users == null || param.users.isEmpty()) DocStatus.APPROVED
                        else DocStatus.PENDING,
                regDate = Date(),
                user = User(userId),
        )
        val saved = docRepository.save(newDoc)

        if(param.users != null && param.users.isNotEmpty()) {
            val appEntities = param.users.mapIndexed { i, it ->
                DocApproval(
                        id = 0,
                        doc = saved,
                        user = User(it),
                        seq = i,
                        status = if(i == 0) ApprovalStatus.RESPOND else ApprovalStatus.WAIT,
                        comment = null,
                        regDate = Date(),
                        modDate = Date()
                )
            }
            docApprovalRepository.saveAll(appEntities)
        }

        return saved.toDto()
    }

    fun getOutbox(userId: String, pageNum: Int = 0, pageSize: Int = 10) =
            docRepository.findByUserAndStatusIn(
                    User(userId),
                    listOf(DocStatus.PENDING),
                    PageRequest.of(pageNum, pageSize, Sort.Direction.ASC, "id")
            ).toDto()

    fun getInbox(userId: String, pageNum: Int = 0, pageSize: Int = 10) =
            docApprovalRepository.findAllByUserAndStatusIn(
                    User(userId),
                    listOf(ApprovalStatus.RESPOND),
                    PageRequest.of(pageNum, pageSize, Sort.Direction.ASC, "regDate")
            ).toDto()

    fun getArchive(userId: String, pageNum: Int = 0, pageSize: Int = 10) =
            docApprovalRepository.findDocCompleted(
                userId,
                PageRequest.of(pageNum, pageSize, Sort.Direction.ASC, "reg_date")
            ).toDocPage()

    @Transactional
    fun sign(userId: String, param: ReqDocSign) {
        val status = try { ApprovalStatus.valueOf(param.decision) }
                     catch (e: IllegalArgumentException) { throw e }

        val docOpt = docRepository.findById(param.docId)

        if (docOpt.isPresent) {
            val docEnt = docOpt.get()
            val approveEnt = docApprovalRepository.findByUserAndDoc(User(userId), docEnt)

            if(approveEnt != null && approveEnt.status == ApprovalStatus.RESPOND) {
                try {
                    docApprovalRepository.save(
                            approveEnt.copy(status = status, comment = param.comment, modDate = Date())
                    )

                    if (status == ApprovalStatus.APPROVED) {
                        val next = docApprovalRepository.findByDocAndSeq(docEnt, approveEnt.seq + 1)
                        if (next != null)
                            docApprovalRepository.save(next.copy(status = ApprovalStatus.RESPOND))
                        else
                            docRepository.save(docEnt.copy(status = DocStatus.APPROVED))
                    } else if (status == ApprovalStatus.DENIED) {
                        val ignores = docApprovalRepository.findAllByDocAndSeqGreaterThan(docEnt, approveEnt.seq)
                        if(ignores.isNotEmpty())
                            docApprovalRepository.saveAll(ignores.map { it.copy(status = ApprovalStatus.IGNORE) })

                        docRepository.save(docEnt.copy(status = DocStatus.DENIED))
                    } else throw RuntimeException("wrong request")
                } catch (e: Exception) {
                    throw e
                }
            } else throw RuntimeException("no doc approval found")
        } else throw RuntimeException("no doc found")
    }
}
