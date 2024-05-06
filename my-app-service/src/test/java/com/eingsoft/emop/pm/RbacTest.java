package com.eingsoft.emop.pm;

import cn.hutool.core.util.StrUtil;
import com.mchen2.myapp.Application;
import com.eingsoft.emop.pm.auth.domain.entity.DomainRbacPolicy;
import com.eingsoft.emop.pm.auth.domain.entity.DomainRoleSubject;
import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.util.EnforceContext;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RbacTest {

    private static final EnforceContext ENFORCE_CONTEXT = new EnforceContext(StrUtil.EMPTY);

    @Test
    public void testAddRole() {
        final Enforcer enforcer = new Enforcer(Application.class.getResource("/casbin/model.conf").getFile());

        String actions = Stream.of("add", "update").map(it -> String.format("^%s$", it)).collect(Collectors.joining("|"));
        final DomainRbacPolicy rule = DomainRbacPolicy.builder().dom("dom").sub("role1").data("project_id")
                .act(actions).build();

        enforcer.addNamedPolicy(ENFORCE_CONTEXT.getpType(), rule.toList());

       // enforcer.removeFilteredNamedPolicy(ENFORCE_CONTEXT.getpType(), 0,
                //rule.getSub(), "", rule.getObj());

        List<List<String>> filterPolicies = enforcer.getFilteredNamedPolicy(ENFORCE_CONTEXT.getpType(), 0,
                rule.getSub(), rule.getDom(), rule.getData());
        System.out.println(filterPolicies);

        DomainRoleSubject subject = DomainRoleSubject.builder().dom(rule.getDom()).role(rule.getSub()).sub("mc").build();
        enforcer.addNamedGroupingPolicy("g", subject.toArray());

        final DomainRbacPolicy request = DomainRbacPolicy.builder().dom(rule.getDom()).sub(subject.getSub())
                .data(rule.getData()).act("add").build();

        Object[] rvals = Stream.concat(Stream.of(ENFORCE_CONTEXT), request.toList().stream())
                .toArray();
        //boolean enforce = enforcer.enforce(rvals);
        //System.out.println(enforce);

        List<List<String>> roles = enforcer.getFilteredNamedGroupingPolicy( "g", 0, "mc", "");
        System.out.println(roles);
        List<List<String>> perms = enforcer.getNamedImplicitPermissionsForUser(ENFORCE_CONTEXT.getpType(), "mc", rule.getDom());
        System.out.println(perms);

        //enforcer.removeFilteredNamedPolicy(ENFORCE_CONTEXT.getpType(), 0, rule.getSub(), rule.getDom());
        // [[sub, role, dom]]
//        List<List<String>> lists = enforcer.getFilteredNamedGroupingPolicy("g", 1, rule.getSub(), rule.getDom());
//        System.out.println(lists);

        //enforcer.removeFilteredNamedGroupingPolicy("g", 1, rule.getSub(), "");
       // enforcer.removeFilteredNamedGroupingPolicy("g", 0, "mc");
//
//        final DomainRbacPolicy rule1 = DomainRbacPolicy.builder().dom("dom").sub(subject.getSub()).data("project_id2")
//                .act(actions).build();
//
//        enforcer.addNamedPolicy(ENFORCE_CONTEXT.getpType(), rule1.toList());
//
//        List<List<String>> permissionsForUser = enforcer.getImplicitPermissionsForUser(subject.getSub(), subject.getDom());
//        System.out.println("aaa" + permissionsForUser);


    }
}
