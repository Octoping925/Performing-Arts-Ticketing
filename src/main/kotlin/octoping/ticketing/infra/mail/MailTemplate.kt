package octoping.ticketing.infra.mail

import octoping.ticketing.domain.ticket.model.Ticket

fun createPurchaseTicketEmail(ticket: Ticket): String {
    return """
        <!DOCTYPE html>
        <html lang="ko">
        <head>
            <meta charset="UTF-8">
            <title>티켓 구매 완료 안내</title>
            <style>
                body {
                    font-family: 'Arial', sans-serif;
                    color: #333;
                    line-height: 1.6;
                    background-color: #f9f9f9;
                    padding: 20px;
                }
                .container {
                    max-width: 600px;
                    margin: auto;
                    background: #fff;
                    padding: 30px;
                    border-radius: 8px;
                    box-shadow: 0 0 10px rgba(0,0,0,0.05);
                }
                h1 {
                    color: #2c3e50;
                }
                .section-title {
                    margin-top: 30px;
                    font-weight: bold;
                    color: #34495e;
                    border-bottom: 1px solid #eee;
                    padding-bottom: 5px;
                }
                .info-table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 10px;
                }
                .info-table td {
                    padding: 8px 0;
                }
                .info-table td:first-child {
                    color: #555;
                    width: 150px;
                }
                .notice {
                    background: #fce4ec;
                    padding: 15px;
                    margin-top: 20px;
                    border-left: 4px solid #ec407a;
                    border-radius: 4px;
                    color: #c2185b;
                    font-size: 0.9em;
                }
                .footer {
                    margin-top: 30px;
                    font-size: 0.9em;
                    color: #888;
                    text-align: center;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <h1>🎟 티켓 구매 완료</h1>
                <p>고객님께서 구매하신 티켓 정보와 결제 내역을 아래와 같이 안내드립니다.</p>

                <div class="section-title">✅ 예매 정보</div>
                <table class="info-table">
                    <tr><td>예매번호</td><td>${ticket.reservationId}}</td></tr>
                    <tr><td>공연/이벤트 ID</td><td>${ticket.artId}</td></tr>
                    <tr><td>좌석 ID</td><td>${ticket.seatId}</td></tr>
                    <tr><td>구매자 ID</td><td>${ticket.boughtUserId}</td></tr>
                    <tr><td>구매 일시</td><td>${ticket.boughtAt}</td></tr>
                </table>

                <div class="section-title">💳 결제 정보</div>
                <table class="info-table">
                    <tr><td>원가</td><td>${ticket.originalPrice}원</td></tr>
                    <tr><td>결제 금액</td><td><strong>${ticket.boughtPrice}원</strong></td></tr>
                </table>

                <div class="notice">
                    <ul>
                        <li>예매 완료 후 티켓은 마이페이지 &gt; 예매 내역에서 확인하실 수 있습니다.</li>
                        <li>티켓 환불 및 변경은 규정에 따라 가능합니다.</li>
                        <li>예매번호를 꼭 확인하시고, 행사 당일 지참해 주세요.</li>
                    </ul>
                </div>

                <div class="footer">
                    감사합니다. 즐거운 관람 되시길 바랍니다.<br>
                </div>
            </div>
        </body>
        </html>
    """.trimIndent()
}
