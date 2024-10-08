package com.example.Sample.SpringTest.grpc;

import MyPackage.ReqResServiceGrpc;
import MyPackage.Request;
import MyPackage.Response;

import com.example.Sample.SpringTest.controller.ObjController;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.devh.boot.grpc.server.service.GrpcService;
import io.grpc.stub.StreamObserver;
import com.example.Sample.SpringTest.controller.TemplateController;
import ObjectMapper.JSON_Parsor;
import com.example.Sample.SpringTest.collection.Template;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@GrpcService
public class EntryPoint extends ReqResServiceGrpc.ReqResServiceImplBase {

    private static final String keyString = "12345678901234567890123456789012";
    public final TemplateController t;
    public final ObjController o;

    @Autowired
    public EntryPoint(TemplateController t, ObjController o) {
        this.t = t;
        this.o = o;
    }

    @Override
    public void saveJson(Request request, StreamObserver<Response> responseObserver) {
        String rawrequest = request.getResponseString();

        String requestString = null;
        try {
            requestString = Aes.decrypt(rawrequest, keyString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String[] reqObject = requestString.split("####");

        String route = null;
        try {
            route = reqObject[0];
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (route.equals("/create/template")) {
            try {

                String json = null;
                try {
                    json = reqObject[1];
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                t.save(JSON_Parsor.fromJson(JSON_Parsor.parse(json), Template.class));
                Response response = Response.newBuilder()
                        .setResult(Aes.encrypt("Done.", keyString))
                        .build();
                // Send the response back to the client
                responseObserver.onNext(response);
                // Complete the call
                responseObserver.onCompleted();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (route.equals("/create/object")) {
            // Get string of objects separated by $
            String json = null;
            try {
                json = reqObject[1];
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Get the number of objects according to the number of $ signs
            int num_objects = 0;
            for (int i = 0; i < json.length(); i++) {
                if (json.charAt(i) == '$') {
                    num_objects++;
                }
            }
            num_objects++;

            // if there's one object then call submitObject function on it
            // else go through all the objects in the string that are split by $
            // and then call submitObject on all of them
            if (num_objects == 1) {
                try {
                    o.submitObject(json);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } else {
                String[] parts = json.split("\\$");
                for (int i=0; i<num_objects; i++) {
                    try {
                        o.submitObject(parts[i]);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            Response response = null;
            try {
                response = Response.newBuilder()
                        .setResult(Aes.encrypt("Done.", keyString))
                        .build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // Send the response back to the client
            responseObserver.onNext(response);
            // Complete the call
            responseObserver.onCompleted();

        } else if (route.startsWith("/template/attachAttributeExpression/")) {
            String[] parts = route.split("/");
            String templateName = parts[3];
            String attributeName = parts[4];
            String expression = parts[5];

            try {
                t.attachExpressionToTemplateAttribute(templateName, attributeName, expression);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            Response response = null;
            try {
                response = Response.newBuilder()
                        .setResult(Aes.encrypt("Expression attached.", keyString))
                        .build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // Send the response back to the client
            responseObserver.onNext(response);
            // Complete the call
            responseObserver.onCompleted();

        } else if (route.startsWith("/template/attachTemplateExpression/")) {
            String[] parts = route.split("/");
            String templateName = parts[3];

            // Get json of expression
            String json = null;
            try {
                json = reqObject[1];
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Modify Json
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(json);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            // Get the value of "expressionString"
            String expression = null;
            try {
                expression = jsonObject.getString("expressionString");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            // Define the regex pattern for words (sequences of alphabetic characters)
            Pattern wordPattern = Pattern.compile("\\b[a-zA-Z]+\\b");
            Matcher matcher = wordPattern.matcher(expression);

            // Use StringBuilder to build the modified string
            StringBuilder modifiedExpression = new StringBuilder();


            List<String> expressions = t.getExpressionListByTemplateName(templateName);
            List<String> attributeNames = t.getAttributeListByTemplateName(templateName);
            while (matcher.find()) {
                String word = matcher.group();

                // Check if the word is in the list
                if (attributeNames.contains(word)) {
                    String replacement = templateName + ".a." + word;
                    matcher.appendReplacement(modifiedExpression, replacement);
                } else if (expressions.contains(word)){
                    String replacement = templateName + ".e." + word;
                    matcher.appendReplacement(modifiedExpression, replacement);
                }
            }

            // Append any remaining text after the last match
            matcher.appendTail(modifiedExpression);

            // Update the JSON object with the modified expressionString
            try {
                jsonObject.put("expressionString", modifiedExpression.toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            try {
                t.attachExpressionToTemplate(jsonObject.toString(), templateName);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            Response response = null;
            try {
                response = Response.newBuilder()
                        .setResult(Aes.encrypt("Attached expression.", keyString))
                        .build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // Send the response back to the client
            responseObserver.onNext(response);
            // Complete the call
            responseObserver.onCompleted();

        } else if (route.startsWith("/template/")) {
            String parameter = route.substring("/template/".length());
            String result = t.getTemplateBytemplatename(parameter);
            Response response = null;

            try {
                response = Response.newBuilder()
                        .setResult(Aes.encrypt(result, keyString))
                        .build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // Send the response back to the client
            responseObserver.onNext(response);
            // Complete the call
            responseObserver.onCompleted();
        }
    }



}