package info.galudisu.comic.system.security

import org.apache.shiro.subject.Subject
import org.apache.shiro.subject.SubjectContext
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory

class StatelessDefaultSubjectFactory : DefaultWebSubjectFactory() {
    override fun createSubject(context: SubjectContext): Subject {
        context.isSessionCreationEnabled = false
        return super.createSubject(context)
    }
}