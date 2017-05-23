package com.kyh;

import com.kyh.statemachine.Events;
import com.kyh.statemachine.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

/**
 * 状态机配置类
 *
 * 用途：封装状态迁移为核心的业务。
 *
 * 优势：使得复杂的业务清晰化。状态、状态迁移事件、监听处理器 层次清晰！
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 定义使用的所有状态以及初始状态
     */
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
            .withStates()
                .initial(States.UNPAID)
                .states(EnumSet.allOf(States.class));
    }

    /**
     * 定义状态的迁移动作
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
            .withExternal()
                .source(States.UNPAID).target(States.WAITING_FOR_RECEIVE)
                .event(Events.PAY)
                .and()
            .withExternal()
                .source(States.WAITING_FOR_RECEIVE).target(States.DONE)
                .event(Events.RECEIVE);
    }

    /**
     * 定义监听处理器
     * EventConfig.java中定义了 注解式 监听处理器
     */
//    @Override
//    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
//            throws Exception {
//        config
//            .withConfiguration()
//                .listener(listener());
//    }
//
//    @Bean
//    public StateMachineListener<States, Events> listener() {
//        return new StateMachineListenerAdapter<States, Events>() {
//
//            @Override
//            public void transition(Transition<States, Events> transition) {
//                if(transition.getTarget().getId() == States.UNPAID) {
//                    logger.info("订单创建，待支付");
//                    return;
//                }
//
//                if(transition.getSource().getId() == States.UNPAID
//                        && transition.getTarget().getId() == States.WAITING_FOR_RECEIVE) {
//                    logger.info("用户完成支付，待收货");
//                    return;
//                }
//
//                if(transition.getSource().getId() == States.WAITING_FOR_RECEIVE
//                        && transition.getTarget().getId() == States.DONE) {
//                    logger.info("用户已收货，订单完成");
//                    return;
//                }
//            }
//
//        };
//    }

}