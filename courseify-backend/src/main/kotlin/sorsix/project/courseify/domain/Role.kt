package sorsix.project.courseify.domain

import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*
import java.util.stream.Collectors

/**
 *  This is not used.
 */
enum class Role(private val permissions: Set<Permission>) {
    USER(Collections.emptySet()),
    ADMIN(
        setOf(
            Permission.ADMIN_READ,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_DELETE,
            Permission.ADMIN_CREATE,
            Permission.MANAGER_READ,
        )
    ),
    MANAGER(
        setOf(
            Permission.MANAGER_READ,
            Permission.MANAGER_UPDATE,
            Permission.MANAGER_DELETE,
            Permission.MANAGER_CREATE
        )
    );

    fun getAuthorities(): List<SimpleGrantedAuthority> {

        val authorities = permissions
            .stream()
            .map { permission -> SimpleGrantedAuthority(permission.permission) }
            .collect(Collectors.toList())
        authorities.add(SimpleGrantedAuthority("ROLE_$name"))
        return authorities

    }
}