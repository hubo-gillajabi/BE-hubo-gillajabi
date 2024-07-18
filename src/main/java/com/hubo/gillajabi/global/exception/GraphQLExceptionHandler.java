package com.hubo.gillajabi.global.exception;

import com.hubo.gillajabi.login.infrastructure.exception.AuthException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof AuthException) {
            return GraphqlErrorBuilder.newError(env)
                    .message(ex.getMessage())
                    .build();
        }
        return null;
    }
}
