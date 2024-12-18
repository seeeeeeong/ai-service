package lee.io.ai.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1701492571L;

    public static final QMember member = new QMember("member1");

    public final lee.io.ai.global.entity.QBaseTimeEntity _super = new lee.io.ai.global.entity.QBaseTimeEntity(this);

    public final ListPath<lee.io.ai.domain.character.entity.Character, lee.io.ai.domain.character.entity.QCharacter> characters = this.<lee.io.ai.domain.character.entity.Character, lee.io.ai.domain.character.entity.QCharacter>createList("characters", lee.io.ai.domain.character.entity.Character.class, lee.io.ai.domain.character.entity.QCharacter.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final EnumPath<lee.io.ai.domain.member.enums.Provider> provider = createEnum("provider", lee.io.ai.domain.member.enums.Provider.class);

    public final StringPath providerUid = createString("providerUid");

    public final EnumPath<lee.io.ai.domain.member.enums.Role> role = createEnum("role", lee.io.ai.domain.member.enums.Role.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

