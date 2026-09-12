package com.spring.implementation.service.impl;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.spring.implementation.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final Resend resend;
    @Value("${resend.api-key}")
    private String apiKey;

    @Value("${resend.from}")
    private String from;





    @Override
    public void sendPasswordResetEmail(
            String email,
            String resetLink
    ) {
        String emailContent = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>CarePlus EHR - Password Reset</title>
        </head>

        <body style="
            margin: 0;
            padding: 0;
            background-color: #f4f8fc;
            font-family: Arial, Helvetica, sans-serif;
        ">

        <table
            width="100%%"
            cellpadding="0"
            cellspacing="0"
            border="0"
            style="
                background-color: #f4f8fc;
                padding: 40px 15px;
            "
        >
            <tr>
                <td align="center">

                    <!-- Main Card -->
                    <table
                        width="600"
                        cellpadding="0"
                        cellspacing="0"
                        border="0"
                        style="
                            width: 100%%;
                            max-width: 600px;
                            background-color: #ffffff;
                            border-radius: 16px;
                            overflow: hidden;
                            border: 1px solid #e5edf5;
                        "
                    >

                        <!-- Header -->
                        <tr>
                            <td
                                align="center"
                                style="
                                    padding: 30px 25px;
                                    border-bottom: 1px solid #edf2f7;
                                "
                            >

                                <img
                                    src="http://localhost:5173/assets/logo.png"
                                    alt="CarePlus EHR"
                                    width="190"
                                    style="
                                        display: block;
                                        width: 190px;
                                        max-width: 100%%;
                                        height: auto;
                                        border: 0;
                                    "
                                >

                                <p style="
                                    margin: 10px 0 0;
                                    color: #7c91aa;
                                    font-size: 13px;
                                    line-height: 20px;
                                ">
                                    Better Care. Healthier Tomorrows.
                                </p>

                            </td>
                        </tr>


                        <!-- Content -->
                        <tr>
                            <td
                                align="center"
                                style="
                                    padding: 45px 40px 35px;
                                "
                            >

                                <!-- Lock Icon -->
                                <table
                                    cellpadding="0"
                                    cellspacing="0"
                                    border="0"
                                    style="margin-bottom: 22px;"
                                >
                                    <tr>
                                        <td
                                            align="center"
                                            valign="middle"
                                            style="
                                                width: 72px;
                                                height: 72px;
                                                border-radius: 50%%;
                                                background-color: #eef7ff;
                                                font-size: 32px;
                                            "
                                        >
                                            🔐
                                        </td>
                                    </tr>
                                </table>


                                <!-- Heading -->
                                <h1 style="
                                    margin: 0;
                                    color: #172b4d;
                                    font-size: 30px;
                                    line-height: 38px;
                                    font-weight: 700;
                                ">
                                    Password Reset
                                </h1>


                                <!-- Description -->
                                <p style="
                                    margin: 18px 0 0;
                                    color: #506784;
                                    font-size: 16px;
                                    line-height: 26px;
                                ">
                                    We received a request to reset your
                                    <strong style="color: #294463;">
                                        CarePlus EHR
                                    </strong>
                                    password.
                                </p>

                                <p style="
                                    margin: 6px 0 0;
                                    color: #7c91aa;
                                    font-size: 14px;
                                    line-height: 22px;
                                ">
                                    Click the button below to set a new password.
                                </p>


                                <!-- Reset Button -->
                                <table
                                    cellpadding="0"
                                    cellspacing="0"
                                    border="0"
                                    style="
                                        margin: 30px auto 0;
                                    "
                                >
                                    <tr>
                                        <td
                                            align="center"
                                            style="
                                                background-color: #1976d2;
                                                border-radius: 10px;
                                            "
                                        >

                                            <a
                                                href="%s"
                                                target="_blank"
                                                style="
                                                    display: inline-block;
                                                    padding: 16px 36px;
                                                    color: #ffffff;
                                                    background-color: #1976d2;
                                                    border-radius: 10px;
                                                    text-decoration: none;
                                                    font-size: 16px;
                                                    font-weight: 600;
                                                "
                                            >
                                                Reset Password
                                                &nbsp;&nbsp;→
                                            </a>

                                        </td>
                                    </tr>
                                </table>


                                <!-- Expiration -->
                                <p style="
                                    margin: 24px 0 0;
                                    color: #6b8099;
                                    font-size: 14px;
                                    line-height: 22px;
                                ">
                                    This link will expire in
                                    <strong style="color: #1976d2;">
                                        15 minutes.
                                    </strong>
                                </p>


                                <!-- Divider -->
                                <table
                                    width="100%%"
                                    cellpadding="0"
                                    cellspacing="0"
                                    border="0"
                                    style="margin-top: 32px;"
                                >
                                    <tr>
                                        <td style="
                                            height: 1px;
                                            background-color: #e5edf5;
                                            font-size: 1px;
                                            line-height: 1px;
                                        ">
                                            &nbsp;
                                        </td>
                                    </tr>
                                </table>


                                <!-- Security Notice -->
                                <table
                                    width="100%%"
                                    cellpadding="0"
                                    cellspacing="0"
                                    border="0"
                                    style="margin-top: 25px;"
                                >
                                    <tr>

                                        <td
                                            width="50"
                                            valign="top"
                                            style="width: 50px;"
                                        >

                                            <div style="
                                                width: 42px;
                                                height: 42px;
                                                line-height: 42px;
                                                border-radius: 50%%;
                                                background-color: #e9f8f1;
                                                text-align: center;
                                                font-size: 18px;
                                                color: #169b83;
                                            ">
                                                ✓
                                            </div>

                                        </td>

                                        <td
                                            valign="top"
                                            align="left"
                                        >

                                            <p style="
                                                margin: 0;
                                                color: #294463;
                                                font-size: 14px;
                                                line-height: 22px;
                                            ">
                                                If you did not request this,
                                                you can safely ignore this email.
                                            </p>

                                            <p style="
                                                margin: 5px 0 0;
                                                color: #7c91aa;
                                                font-size: 13px;
                                                line-height: 20px;
                                            ">
                                                For your security, do not share
                                                this reset link with anyone.
                                            </p>

                                        </td>

                                    </tr>
                                </table>


                                <!-- Features -->
                                <table
                                    width="100%%"
                                    cellpadding="0"
                                    cellspacing="0"
                                    border="0"
                                    style="margin-top: 32px;"
                                >
                                    <tr>

                                        <!-- Better Care -->
                                        <td
                                            width="33.33%%"
                                            align="center"
                                            valign="top"
                                            style="
                                                width: 33.33%%;
                                                padding: 0 5px;
                                            "
                                        >

                                            <div style="
                                                width: 42px;
                                                height: 42px;
                                                line-height: 42px;
                                                margin: 0 auto 9px;
                                                border-radius: 50%%;
                                                background-color: #eef7ff;
                                                font-size: 18px;
                                            ">
                                                ♥
                                            </div>

                                            <p style="
                                                margin: 0;
                                                color: #294463;
                                                font-size: 13px;
                                                font-weight: 600;
                                            ">
                                                Better Care
                                            </p>

                                            <p style="
                                                margin: 3px 0 0;
                                                color: #7c91aa;
                                                font-size: 12px;
                                            ">
                                                For Every Patient
                                            </p>

                                        </td>


                                        <!-- Secure -->
                                        <td
                                            width="33.33%%"
                                            align="center"
                                            valign="top"
                                            style="
                                                width: 33.33%%;
                                                padding: 0 5px;
                                                border-left: 1px solid #e5edf5;
                                                border-right: 1px solid #e5edf5;
                                            "
                                        >

                                            <div style="
                                                width: 42px;
                                                height: 42px;
                                                line-height: 42px;
                                                margin: 0 auto 9px;
                                                border-radius: 50%%;
                                                background-color: #e9f8f1;
                                                font-size: 18px;
                                            ">
                                                ✓
                                            </div>

                                            <p style="
                                                margin: 0;
                                                color: #294463;
                                                font-size: 13px;
                                                font-weight: 600;
                                            ">
                                                Secure &amp; Trusted
                                            </p>

                                            <p style="
                                                margin: 3px 0 0;
                                                color: #7c91aa;
                                                font-size: 12px;
                                            ">
                                                Healthcare Platform
                                            </p>

                                        </td>


                                        <!-- Healthier -->
                                        <td
                                            width="33.33%%"
                                            align="center"
                                            valign="top"
                                            style="
                                                width: 33.33%%;
                                                padding: 0 5px;
                                            "
                                        >

                                            <div style="
                                                width: 42px;
                                                height: 42px;
                                                line-height: 42px;
                                                margin: 0 auto 9px;
                                                border-radius: 50%%;
                                                background-color: #eef7ff;
                                                font-size: 18px;
                                            ">
                                                ♡
                                            </div>

                                            <p style="
                                                margin: 0;
                                                color: #294463;
                                                font-size: 13px;
                                                font-weight: 600;
                                            ">
                                                Healthier
                                            </p>

                                            <p style="
                                                margin: 3px 0 0;
                                                color: #7c91aa;
                                                font-size: 12px;
                                            ">
                                                Tomorrows
                                            </p>

                                        </td>

                                    </tr>
                                </table>

                            </td>
                        </tr>


                        <!-- Footer -->
                        <tr>
                            <td
                                align="center"
                                style="
                                    padding: 32px 25px 28px;
                                    background-color: #f5faff;
                                    border-top: 1px solid #edf3f8;
                                "
                            >

                                <p style="
                                    margin: 0;
                                    color: #172b4d;
                                    font-size: 17px;
                                    line-height: 24px;
                                    font-weight: 700;
                                ">
                                    CarePlus EHR
                                </p>

                                <p style="
                                    margin: 6px 0 0;
                                    color: #7c91aa;
                                    font-size: 13px;
                                    line-height: 20px;
                                ">
                                    Secure. Simple. Patient-Focused.
                                </p>

                                <p style="
                                    margin: 20px 0 0;
                                    color: #9aabbd;
                                    font-size: 11px;
                                    line-height: 18px;
                                ">
                                    This is an automated message from
                                    CarePlus EHR.
                                    Please do not reply to this email.
                                </p>

                            </td>
                        </tr>

                    </table>

                </td>
            </tr>
        </table>

        </body>
        </html>
        """.formatted(resetLink);


        CreateEmailOptions params =
                CreateEmailOptions.builder()
                        .from(from)
                        .to(email)
                        .subject("Reset your EHR password")
                        .html(emailContent)
                        .build();

        try {
            resend.emails().send(params);
        } catch (ResendException e) {
            throw new RuntimeException(
                    "Failed to send password reset email",
                    e
            );
        }
    }


}
